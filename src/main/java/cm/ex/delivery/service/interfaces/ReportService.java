package cm.ex.delivery.service.interfaces;

import cm.ex.delivery.entity.Report;
import cm.ex.delivery.entity.User;
import cm.ex.delivery.response.BasicResponse;

import java.util.List;

public interface ReportService {

    public BasicResponse createReport(String report);

    public List<Report> listReportBySenderId(User senderId);

    public List<Report> listAllReports();
}
