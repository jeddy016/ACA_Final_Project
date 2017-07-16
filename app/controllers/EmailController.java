package controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClient;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;
import models.EmailDetail;
import models.ServiceDetail;
import models.User;
import play.Logger;
import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;

public class EmailController extends Controller
{
    private final JPAApi jpaApi;

    @Inject
    public EmailController(JPAApi jpaApi)
    {
        this.jpaApi = jpaApi;
    }

    @Transactional
    public Result sendEmails() throws IOException
    {
        List<User> users = jpaApi.em().createQuery("SELECT u FROM User u WHERE u.notificationsOptIn = 1", User.class).getResultList();

        for(User user : users)
        {
            LocalDate today = LocalDate.now();
            boolean alreadyNotified = today.getMonth().equals(user.getLastNotified().getMonth());

            if(!alreadyNotified)
            {
                List<EmailDetail> emailDetails = jpaApi.em().createNativeQuery("SELECT v.vehicle_nickname as vehicleName, s.service_id as id, st.type_name as serviceName, s.miles_til_due as milesTilDue FROM service_type st JOIN service s ON st.service_type_id = s.service_type_id JOIN vehicle v ON v.vehicle_id = s.vehicle_id JOIN user u ON u.user_id = v.user_id WHERE v.user_id = :id AND s.tracked = 1 AND (s.miles_til_due <= u.notifications_miles_ahead) ORDER BY v.vehicle_nickname", EmailDetail.class).setParameter("id", user.getUserID()).getResultList();

                String from = "jeddy016@gmail.com";
                String to= user.getEmail();
                String subject= "PitStop Monthly Update";
                String body = formatBody(emailDetails, user.getNotificationsMilesAhead());

                send(from, to, body, subject);

                //TODO un-comment this
                //user.setLastNotified(today);
            }
        }
        return ok("success");
    }

    private static void send(String from, String to, String body, String subject) throws IOException
    {
        // Construct an object to contain the recipient address.
        Destination destination = new Destination().withToAddresses(new String[]{to});

        // Create the subject and body of the message.
        Content subjectLine = new Content().withData(subject);
        Content textBody = new Content().withData(body);
        Body bodyContent = new Body().withHtml(textBody);  //.withText(textBody);

        // Create a message with the specified subject and body.
        Message message = new Message().withSubject(subjectLine).withBody(bodyContent);

        // Assemble the email.
        SendEmailRequest request = new SendEmailRequest().withSource(from).withDestination(destination).withMessage(message);

        try
        {
            System.out.println("Attempting to send an email through Amazon SES by using the AWS SDK for Java...");

            AWSCredentials credentials = null;

            try
            {
                credentials = new ProfileCredentialsProvider().getCredentials();

            } catch (Exception e)
            {
                throw new AmazonClientException(
                        "Cannot load the credentials from the credential profiles file. " +
                        "Please make sure that your credentials file is at the correct " +
                        "location (~/.aws/credentials), and is in valid format.", e);
            }

            // Instantiate an Amazon SES client, which will make the service call with the supplied AWS credentials.
            AmazonSimpleEmailServiceClient client = new AmazonSimpleEmailServiceClient(credentials);

            // Choose the AWS region of the Amazon SES endpoint you want to connect to.
            Region REGION = Region.getRegion(Regions.US_EAST_1);
            client.setRegion(REGION);

            // Send the email.
            client.sendEmail(request);
            Logger.debug("Email Sent!");

        } catch (Exception ex)
        {
            System.out.println("The email was not sent.");
            System.out.println("Error message: " + ex.getMessage());
        }
    }

    private static String formatBody(List<EmailDetail> emailDetails, int notificationsMilesAhead)
    {
        StringBuilder body = new StringBuilder();

        if(emailDetails.size() > 0)
        {
            String vehicle = "";
            body.append("<head><style> h2 {color: red;} </style></head><body>");

            body.append("<h2>Here are the services coming up in " + notificationsMilesAhead + " miles:</h2>");

            for (EmailDetail detail : emailDetails)
            {
                if(!detail.getVehicleName().equals(vehicle))
                {
                    vehicle = detail.getVehicleName();

                    body.append("<h4>" + detail.getVehicleName() + ": </h4>");

                    if(Integer.parseInt(detail.getMilesTilDue()) > 0)
                    {
                        body.append("<p>" + detail.getServiceName() + " in " + detail.getMilesTilDue() + " miles. </p>");
                    }
                    else
                    {
                        body.append("<p>" + detail.getServiceName() + " overdue by " + (-1 * Integer.parseInt(detail.getMilesTilDue())) + " miles. </p>");
                    }
                }
                else
                {
                    body.append("<p>" + detail.getServiceName() + " in " + detail.getMilesTilDue() + " miles. </p>");
                }
            }
        }
        else
        {
            body.append("<h1> You have no vehicles due for service in " + notificationsMilesAhead + " miles.</h1>");
        }

        body.append("<br><br><br><p>**To change the services you see in these reminders, click the \"Edit Vehicle\" button found on your dashboard and select the services you wish to track. You can change how many miles in advance you would like to receive reminders for services from the Edit Profile screen.</p></body>");

        return body.toString();
    }
}