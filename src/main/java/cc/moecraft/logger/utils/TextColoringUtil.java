package cc.moecraft.logger.utils;

import cc.moecraft.logger.coloring.*;
import cc.moecraft.logger.format.AnsiColor;
import cc.moecraft.logger.text.Paragraph;
import lombok.AllArgsConstructor;

import java.awt.*;

/**
 * 此类由 Hykilpikonna 在 2018/07/05 创建!
 * Created by Hykilpikonna on 2018/07/05!
 * Github: https://github.com/hykilpikonna
 * QQ: admin@moecraft.cc -OR- 871674895
 *
 * @author Hykilpikonna
 */
@AllArgsConstructor
public class TextColoringUtil
{
    private String text;
    private AnsiColorMode colorMode;
    
    public String getGradientText(MultiPointLinearGradient gradient)
    {
        char[] chars = text.toCharArray();
        AnsiRGB[] colors = gradient.getColors(colorMode, chars.length);

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < chars.length; i++)
        {
            result.append(colors[i].toString()).append(chars[i]);
        }

        return result.toString();
    }

    public String getGradientText(Color color1, Color color2, Color ... colors)
    {
        return getGradientText(new MultiPointLinearGradient(color1, color2, colors));
    }

    public static Paragraph getGradientParagraph(AnsiColorMode colorMode, char[][] chars, MultiPointLinearGradient gradient, int degrees)
    {
        // x = 一句里最多多少字符
        // y = 一共多少句

        int yMax = chars.length - 1;
        int xMax = chars[0].length - 1;

        double slope = Math.tan(Math.toRadians(degrees)); // y = slope * x

        // 0. 获取Offset
        
        // Offset算法:
        //
        //    获取的渐变颜色的行数, offset = 8
        //    ↓
        //     0  =\
        //     1  = \
        //     2  =  \
        //     3  =   \
        //     4  =    \
        //     5  =     \
        //     6  =      \
        //     7  =       \
        //     8  = 角度 ( \
        //   0 9  ##########
        //   1 10 ###文字###
        //   2 11 ##########
        //   ↑
        //   实际文字的行数

        int offset = (int) (slope * xMax);
        int yWithOffset = yMax + offset;

        // 1. 获取Color[][]

        // 获取算法:
        //
        // 从一条1维的颜色数组按照倾斜度(slope)映射进去
        //
        //     0  =\
        //     1  =\\
        //     2  =\\\
        //     3  =\\\\
        //     4  =\\\\\
        //     5  =\\\\\\
        //     6  =\\\\\\\
        //     7  =\\\\\\\\
        //     8  =\\\\\\\\\
        //   0 9  |--------|\
        //   1 10 |\\文字\\|\\
        //   2 11 |________|\\\


        AnsiRGB[][] newColors = new AnsiRGB[yWithOffset + 1][xMax + 1];

        AnsiRGB[] verticalColors = gradient.getColors(colorMode, yWithOffset + 1);

        for (int sourceY = 0; sourceY < verticalColors.length; sourceY++)
        {
            AnsiRGB color = verticalColors[sourceY];

            for (int x = 0; x < xMax + 1; x++)
            {
                // 从斜度(slope)用X获取实际点
                int newY = sourceY + (int) (slope * x);

                try
                {
                    newColors[newY][x] = color;
                }
                catch (IndexOutOfBoundsException e)
                {
                    break;
                }
            }
        }

        // 2. 把颜色二维数组映射到实际文字

        Paragraph result = new Paragraph();

        for (int y = 0; y < yMax + 1; y++)
        {
            char[] sentence = chars[y];
            AnsiRGB[] colors = newColors[y + offset];

            StringBuilder oneResult = new StringBuilder();

            for (int x = 0; x < xMax + 1; x++)
            {
                char charInASentence = sentence[x];
                AnsiRGB colorInASentence = colors[x];

                if (charInASentence == '\u0000') continue;
                if (colorInASentence != null) oneResult.append(colorInASentence);
                else oneResult.append(AnsiColor.RESET);

                oneResult.append(charInASentence);
            }

            result.addSentences(oneResult.toString());
        }

        return result;
    }
}
