package com.yajun.green.common.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.util.Random;

/**
 * User: Jack Wang
 * Date: 17-10-9
 * Time: 上午8:59
 */
public class ValidateCodeUtils {

    private static int width = 80;

    private static int height = 25;

    private static int codeCount = 4;

    /**
     * xx
     */
    private static int xx = 0;

    private static int fontHeight;

    /**
     * codeY
     */
    private static int codeY;

    /**
     * codeSequence
     */
    static char[] codeSequence = {'1', '2', '3', '4', '5', '6', '7', '8', '9', '0'};

    public static void create(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        init();
        service(req, resp);
    }

    public static void init() {
        xx = (width - 15) / codeCount;
        fontHeight = height - 2;
        codeY = height - 4;
    }

    /**
     * @param req
     * @param resp
     * @throws ServletException
     * @throws java.io.IOException
     */
    public static void service(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, java.io.IOException {
        BufferedImage buffImg = new BufferedImage(width, height,
                BufferedImage.SCALE_DEFAULT);
        Graphics2D gd = buffImg.createGraphics();

        Random random = new Random();

        gd.setColor(Color.WHITE);
        gd.fillRect(0, 0, width, height);

        Font font = new Font("Oregon LDO", Font.PLAIN, fontHeight);
        gd.setFont(font);

        gd.setColor(Color.BLACK);
        gd.drawRect(0, 0, width - 1, height - 1);

        gd.setColor(Color.BLACK);
        for (int i = 0; i < 6; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            gd.drawLine(x, y, x + xl, y + yl);
        }

        StringBuffer randomCode = new StringBuffer();

        for (int i = 0; i < codeCount; i++) {
            String strRand = String.valueOf(codeSequence[random.nextInt(10)]);
            gd.setColor(Color.BLACK);
            gd.drawString(strRand, i * xx + 3, codeY);
            randomCode.append(strRand);
        }
        HttpSession session = req.getSession(true);
        session.setAttribute("validateCode", randomCode.toString());
        resp.setHeader("Pragma", "no-cache");
        resp.setHeader("Cache-Control", "no-cache");
        resp.setDateHeader("Expires", 0);
        resp.setContentType("image/png");

        ServletOutputStream sos = resp.getOutputStream();
        ImageIO.write(buffImg, "png", sos);
        sos.close();
    }
}
