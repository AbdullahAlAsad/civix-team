package com.getcivix.app.Models;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class ReportModel {

    public String comment;
    public Long reportTime;
    public String reportLocation;
    public String reporterUserID;

    public ReportModel() {

    }

    public ReportModel(String comment, Long reportTime, String reportLocation, String reporterUserID) {
        this.comment = comment;
        this.reportTime = reportTime;
        this.reportLocation = reportLocation;
        this.reporterUserID = reporterUserID;
    }

    @Override
    public String toString() {
        return "ReportModel{" +
                "comment='" + comment + '\'' +
                ", reportTime=" + reportTime +
                ", reportLocation='" + reportLocation + '\'' +
                ", reporterUserID='" + reporterUserID + '\'' +
                '}';
    }
}
