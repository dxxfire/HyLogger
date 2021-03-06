import cc.moecraft.logger.HyLogger;
import cc.moecraft.logger.LoggerInstanceManager;
import cc.moecraft.logger.environments.*;
import cc.moecraft.logger.text.Paragraph;

import java.awt.*;

import static cc.moecraft.logger.coloring.GradientPresets.*;

/**
 * 此类由 Hykilpikonna 在 2018/05/04 创建!
 * Created by Hykilpikonna on 2018/05/04!
 * Github: https://github.com/hykilpikonna
 * QQ: admin@moecraft.cc -OR- 871674895
 *
 * @author Hykilpikonna
 */
public class LoggerTest
{
    private static LoggerInstanceManager loggerInstanceManager;

    public static void main(String[] args)
    {
        loggerInstanceManager = new LoggerInstanceManager(new ConsoleColoredEnv(ColorSupportLevel.FORCED), new FileEnv("logs", "log"));

        HyLogger logger = loggerInstanceManager.getLoggerInstance("LoggerTest", true);

        logger.timing.init();
        logger.timing.timeAndReset();

        logger.log("一条测试Log消息");

        logger.timing.timeAndReset();

        logger.debug("一条测试Debug消息");

        logger.timing.timeAndReset();

        logger.error("一条测试Error消息");

        logger.timing.timeAndReset();

        logger.warning("一条测试Warning消息");

        logger.timing.timeAndReset();

        logger.fancy.logRAINBOW("测试随机颜色消息");

        logger.timing.timeAndReset();

        logger.fancy.logGradient("测试渐变从深蓝到浅蓝", Color.BLUE, Color.CYAN);

        logger.timing.timeAndReset();

        logger.fancy.logGradient("测试渐变从橘色到浅蓝", Color.ORANGE, Color.CYAN);

        logger.timing.timeAndReset();

        logger.fancy.logGradient("测试黄绿渐变到天蓝",
                new Color(0, 242, 96),
                new Color(80, 161, 230));

        logger.timing.timeAndReset();

        logger.fancy.logGradient("测试橙色渐变到粉色",
                new Color(255, 140, 0),
                new Color(255, 0, 128));

        logger.timing.timeAndReset();

        logger.fancy.logGradient("##############测试彩虹多点渐变预设##############", RAINBOW);

        logger.timing.timeAndReset();

        logger.fancy.logGradient("##############测试蓝到紫到红多点渐变##############",BPR);

        logger.timing.timeAndReset();

        {
            logger.log("测试Paragraph斜向线性渐变 #1:");

            Paragraph paragraph = new Paragraph("" +
                    "┬ ┬┬ ┬┬  ┌─┐┌─┐┌─┐┌─┐┬─┐",
                    "├─┤└┬┘│  │ ││ ┬│ ┬├┤ ├┬┘",
                    "┴ ┴ ┴ ┴─┘└─┘└─┘└─┘└─┘┴└─"
            );

            logger.fancy.logGradient(paragraph, BPR, 15);
        }

        logger.timing.timeAndReset();

        {
            logger.log("测试Paragraph斜向线性渐变 #2:");

            Paragraph paragraph = new Paragraph("" +
                    "    __  __      __                               ",
                    "   / / / /_  __/ /   ____  ____ _____ ____  _____",
                    "  / /_/ / / / / /   / __ \\/ __ `/ __ `/ _ \\/ ___/",
                    " / __  / /_/ / /___/ /_/ / /_/ / /_/ /  __/ /    ",
                    "/_/ /_/\\__, /_____/\\____/\\__, /\\__, /\\___/_/     ",
                    "      /____/            /____//____/             "
            );

            logger.fancy.logGradient(paragraph, BOP, 60);
        }

        logger.timing.timeAndReset();
    }
}
