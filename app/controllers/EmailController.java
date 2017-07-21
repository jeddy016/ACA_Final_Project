package controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClient;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
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
        List<User> users = jpaApi.em().createQuery("SELECT u FROM User u WHERE u.notificationsOptIn = 2", User.class).getResultList();

        for(User user : users)
        {
            LocalDate today = LocalDate.now();
            boolean alreadyNotified = today.getMonth().equals(user.getLastNotified().getMonth());

            if(!alreadyNotified)
            {
                @SuppressWarnings("unchecked")
                List<EmailDetail> emailDetails = jpaApi.em().createNativeQuery("SELECT v.vehicle_nickname as vehicleName, s.service_id as id, st.type_name as serviceName, s.miles_til_due as milesTilDue FROM service_type st JOIN service s ON st.service_type_id = s.service_type_id JOIN vehicle v ON v.vehicle_id = s.vehicle_id JOIN user u ON u.user_id = v.user_id WHERE v.user_id = :id AND s.tracked = 1 AND (s.miles_til_due <= u.notifications_miles_ahead) ORDER BY v.vehicle_nickname", EmailDetail.class).setParameter("id", user.getUserID()).getResultList();

                String from = "jeddy016@gmail.com";
                String to= user.getEmail();
                String subject= "PitStop Monthly Update";
                String body = formatBody(emailDetails, user.getNotificationsMilesAhead(), user.getFirstName());

                send(from, to, body, subject);

               //TODO: un-comment this line in production to only email users once a month
               // user.setLastNotified(today);
            }
        }
        return ok("success");
    }

    private static void send(String from, String to, String body, String subject) throws IOException
    {
        Destination destination = new Destination().withToAddresses(to);

        Content subjectLine = new Content().withData(subject);
        Content textBody = new Content().withData(body);
        Body bodyContent = new Body().withHtml(textBody);

        Message message = new Message().withSubject(subjectLine).withBody(bodyContent);

        SendEmailRequest request = new SendEmailRequest().withSource(from).withDestination(destination).withMessage(message);

        try
        {
            System.out.println("Attempting to send an email through Amazon SES by using the AWS SDK for Java...");

            // Instantiate an Amazon SES client, which will make the service call with the supplied AWS credentials.
            AmazonSimpleEmailService client = AmazonSimpleEmailServiceClientBuilder.standard().withCredentials(new ProfileCredentialsProvider()).withRegion(Regions.US_EAST_1).build();

            // Send the email.
            client.sendEmail(request);
            Logger.debug("Email Sent!");

        } catch (Exception ex)
        {
            System.out.println("The email was not sent.");
            System.out.println("Error message: " + ex.getMessage());
        }
    }

    private static String formatBody(List<EmailDetail> emailDetails, int notificationsMilesAhead, String name)
    {
        StringBuilder body = new StringBuilder();

        if(emailDetails.size() > 0)
        {
            String vehicle = "";
            body.append("<html><head><style> body {height: 100%; width: 100%;} table {height: 100%; width: 100%; background: #232323; padding: 15px; border: 2px solid #00BCD4} h1, p { color: #00BCD4;} h4, h2, a { color: white !important; font-family: sans-serif;} h1 {text-align: center; margin: 2px !important; font-family: sans-serif; font-size: 50px; text-shadow: -2px 1px 3px #000000;} h2 {font-size: 20px; text-align: center; text-shadow: -2px 1px 3px #000000;} h4 {font-size: 18px; text-decoration: underline; text-shadow: -2px 1px 3px #000000;} p {margin-left: 5px; font-size: 16px; text-shadow: -2px 1px 3px #000000;} a {text-decoration: none !important;} </style></head><body><table>");

            body.append("<a href='http://localhost:9000'><h1>PitStop</h1></a> <h2>Hello ")
                    .append(name)
                    .append("! Here is your monthly vehicle maintenance update: </h2>");

            for (EmailDetail detail : emailDetails)
            {
                if(!detail.getVehicleName().equals(vehicle))
                {
                    vehicle = detail.getVehicleName();

                    body.append("<h4>")
                            .append(detail.getVehicleName())
                            .append(": </h4>");

                    if(Integer.parseInt(detail.getMilesTilDue()) > 0)
                    {
                        body.append("<p>").append(detail.getServiceName()).append(" in ")
                                .append(detail.getMilesTilDue())
                                .append(" miles. </p>");
                    }
                    else
                    {
                        body.append("<p>").append(detail.getServiceName())
                                .append(" overdue by ")
                                .append(-1 * Integer.parseInt(detail.getMilesTilDue()))
                                .append(" miles. </p>");
                    }
                }
                else
                {
                    body.append("<p>").append(detail.getServiceName())
                            .append(" in ")
                            .append(detail.getMilesTilDue())
                            .append(" miles. </p>");
                }
            }
        }
        else
        {
            body.append("<h1> You have no vehicles due for service in ").append(notificationsMilesAhead).append(" miles.</h1>");
        }

        body.append("<br><br><br><p>**To change the services you see in these reminders, click the \"Edit Vehicle\" button found on your dashboard and select the services you wish to track. You can change how many miles in advance you would like to receive reminders for services from the Edit Profile screen.</p></table></body></html>");

        return body.toString();
    }
}