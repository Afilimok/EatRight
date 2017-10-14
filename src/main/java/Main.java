import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws TelegramApiRequestException {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");

        TelegramBotsApi telegramBotsApi = (TelegramBotsApi) ctx.getBean("telegramBotsApi");
        EatRightBot eatRightBot = (EatRightBot) ctx.getBean("eatRightBot");

        telegramBotsApi.registerBot(eatRightBot);

        logger.info("EatRightBot session started!");
    }
}