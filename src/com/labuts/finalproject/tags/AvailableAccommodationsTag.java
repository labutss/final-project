package com.labuts.finalproject.tags;

import com.labuts.finalproject.entity.Accommodation;
import com.labuts.finalproject.exception.ServiceException;
import com.labuts.finalproject.service.BaseService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * AvailableAccommodationsTag is used to print information about available accommodations
 */
@SuppressWarnings("serial")
public class AvailableAccommodationsTag extends TagSupport{
    /**
     * No available accommodations label
     */
    private static final String NO_AVAILABLE_ACCOMMODATIONS = "label.no.available.accommodations";
    /**
     * Accommodation mark label
     */
    private static final String ACCOMMODATION_MARK = "label.accommodation.mark";
    /**
     * Accommodation cost label
     */
    private static final String ACCOMMODATION_COST = "label.accommodation.cost";
    /**
     * Available accommodations label
     */
    private static final String AVAILABLE_ACCOMMODATIONS = "label.available.accommodations";

    private static final Logger logger = LogManager.getLogger();

    @Override
    public int doStartTag() throws JspException {
        List<Accommodation> availableAccommodations = new ArrayList<>();
        BaseService baseService = new BaseService();
        try {
            availableAccommodations = baseService.findAvailableAccommodations();
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
        }
        ResourceBundle bundle = ResourceBundle.getBundle("resources.content", new Locale("ru", "RU"));
        try {
            JspWriter out = pageContext.getOut();
            if(availableAccommodations.isEmpty()){
                out.write("<br>\n" +
                        "    <div class=\"alert alert-info\">\n" +
                        "        <h5>\n" + bundle.getString(NO_AVAILABLE_ACCOMMODATIONS) +
                        "        </h5>\n" +
                        "    </div>");
            }else {
                out.write("<h5 class=\"font-weight-light\">" + bundle.getString(AVAILABLE_ACCOMMODATIONS) + "</h5>");
                for(Accommodation accommodation: availableAccommodations){
                    out.write("<div class=\"card\" style=\"background-color: #e3f2fd;\">\n" +
                            "                <div class=\"card-header font-weight-bold\">\n" +
                            "                    <jsp:text>" + accommodation.getAccommodationName() + "</jsp:text>\n" +
                            "                </div>\n" +
                            "                <div class=\"card-body\">\n" +
                            "                    <p class=\"card-text font-weight-normal\">\n" +
                            "                        <jsp:text>" + accommodation.getAccommodationDescription() + "</jsp:text>\n" +
                            "                    </p>\n" +
                            "                </div>\n" +
                            "                <div class=\"card-footer font-weight-bold\">\n" +
                            bundle.getString(ACCOMMODATION_COST) + ": " +
                            accommodation.getAccommodationCost() + "<br>\n" +
                            bundle.getString(ACCOMMODATION_MARK) + ": " +
                            accommodation.getAccommodationMark() + "</div></div><br>");
                }
            }
        } catch (IOException e) {
            throw new JspException(e.getMessage());
        }
        return SKIP_BODY;
    }
    @Override public int doEndTag() throws JspException {   return EVAL_PAGE; }
}
