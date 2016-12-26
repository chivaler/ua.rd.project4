package ua.rd.project4.controller.util;

import ua.rd.project4.model.services.CarRequestService;
import ua.rd.project4.model.services.impl.DefaultServiceFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.Locale;

public class CarCalendarTag extends TagSupport {
    private final static CarRequestService carRequestService = DefaultServiceFactory.getInstance().getCarRequestService();
    private int carId;

    public void setCarId (String value) {
        try {
            this.carId = Integer.parseInt(value);
        } catch (Exception e) {
            carId = 0;
        }
    }

    @Override
    public int doStartTag() throws JspException {
        try {
            pageContext.getOut().print("<div align=\"center\"><table><tr>\n");
            pageContext.getOut().print("<th>Mon</th><th>Tue</th><th>Wed</th><th>Thur</th><th>Fir</th><th>Sat</th><th>Sun</th></tr>\n");

            TemporalField weekISO = WeekFields.of(Locale.FRANCE).dayOfWeek();
            LocalDate firstMonday = LocalDate.now().withDayOfMonth(1).with(weekISO, 1);
            LocalDate lastMonday = LocalDate.now().plusMonths(1).withDayOfMonth(1).minusDays(1).with(weekISO, 1);

            for (LocalDate currentMinday = firstMonday;currentMinday.compareTo(lastMonday)<=0;currentMinday=currentMinday.plusDays(7)) {
                pageContext.getOut().print("<tr>\n");
                for (int i=0;i<7;i++) {
                    LocalDate currentWeekDay = currentMinday.plusDays(i);
                    String colorOfCell = "";
                    CarRequestService.CarRequestStatus status = carRequestService.isDatesAvalable(Date.valueOf(currentWeekDay),Date.valueOf(currentWeekDay),carId,0);
                    pageContext.getOut().print("<td class=\""+status+"\">"+currentWeekDay.getDayOfMonth()+"</td>");
                }
                pageContext.getOut().print("</tr>\n");
            }

            pageContext.getOut().print(firstMonday+"\n");
            pageContext.getOut().print(lastMonday+"\n");



            pageContext.getOut().print("</table></div>\n");
        } catch (IOException ioException) {
            throw new JspException("Error: " + ioException.getMessage());
        }
        return SKIP_BODY;
    }
}
