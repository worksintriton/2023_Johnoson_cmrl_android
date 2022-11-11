package com.triton.johnson.api;

import com.triton.johnson.model.ImageUploadResponse;
import com.triton.johnson.requestpojo.AttendanceCreateRequest;
import com.triton.johnson.requestpojo.CMRLTicketListRequest;
import com.triton.johnson.requestpojo.CheckAttendanceRequest;
import com.triton.johnson.requestpojo.CmrlDashboardCountRequest;
import com.triton.johnson.requestpojo.FBTokenUpdateRequest;
import com.triton.johnson.requestpojo.FaultTypeListRequest;
import com.triton.johnson.requestpojo.GetPartListRequest;
import com.triton.johnson.requestpojo.JobNoListRequest;
import com.triton.johnson.requestpojo.JohnsonTicketListRequest;
import com.triton.johnson.requestpojo.LoginRequest;
import com.triton.johnson.requestpojo.LogoutRequest;
import com.triton.johnson.requestpojo.ServingLevelListRequest;
import com.triton.johnson.requestpojo.StationNameRequest;
import com.triton.johnson.requestpojo.TicketCreateRequest;
import com.triton.johnson.requestpojo.UpdateIsuesStatusRequest;
import com.triton.johnson.requestpojo.ViewTicketsRequest;
import com.triton.johnson.responsepojo.CMRLTicketListResponse;
import com.triton.johnson.responsepojo.CheckAttendanceResponse;
import com.triton.johnson.responsepojo.CmrlDashboardCountResponse;
import com.triton.johnson.responsepojo.FBTokenUpdateResponse;
import com.triton.johnson.responsepojo.FaultTypeListResponse;
import com.triton.johnson.responsepojo.GetPartListResponse;
import com.triton.johnson.responsepojo.JobNoListResponse;
import com.triton.johnson.responsepojo.JobNumberResponse;
import com.triton.johnson.responsepojo.JohnsonTicketListResponse;
import com.triton.johnson.responsepojo.LoginResponse;
import com.triton.johnson.responsepojo.ServingLevelListResponse;
import com.triton.johnson.responsepojo.StationNameResponse;
import com.triton.johnson.responsepojo.SuccessResponse;
import com.triton.johnson.responsepojo.ViewTicketsResponse;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
/**
 * Created by Iddinesh.
 */



public interface APIInterface {

    /*login*/
    @POST("userdetails/mobile/login")
    Call<LoginResponse> LoginResponseCall(@Header("Content-Type") String type, @Body LoginRequest loginRequest);


    /*station name list*/
    @POST("station_name/getlist_by_type")
    Call<StationNameResponse> StationNameResponseCall(@Header("Content-Type") String type, @Body StationNameRequest stationNameRequest);

    /*Job No list*/
    @POST("job_no/getlist_by_station_id")
    Call<JobNoListResponse> JobNoListResponseCall(@Header("Content-Type") String type, @Body JobNoListRequest jobNoListRequest);

    /*Serving level list*/
    @POST("serving_level/getlist_by_jobno_id")
    Call<ServingLevelListResponse> ServingLevelListResponseCall(@Header("Content-Type") String type, @Body ServingLevelListRequest servingLevelListRequest);


    /*Fault type list*/
    @POST("fault_type/getlist_by_type")
    Call<FaultTypeListResponse> FaultTypeListResponseCall(@Header("Content-Type") String type, @Body FaultTypeListRequest faultTypeListRequest);


    /*Ticket create*/
    @POST("ticket/create")
    Call<SuccessResponse> TicketCreateRequestCall(@Header("Content-Type") String type, @Body TicketCreateRequest ticketCreateRequest);

    /*CMRL Ticket list*/
    @POST("ticket/getlist_by_type")
    Call<CMRLTicketListResponse> CMRLTicketListResponseCall(@Header("Content-Type") String type, @Body CMRLTicketListRequest cmrlTicketListRequest);

    /*Johnson Ticket list*/
    @POST("ticket/johnson_getlist_by_station")
    Call<JohnsonTicketListResponse> JohnsonTicketListResponseCall(@Header("Content-Type") String type, @Body JohnsonTicketListRequest johnsonTicketListRequest);

    /*Update issues status*/
    @POST("tickethistory/create")
    Call<SuccessResponse> UpdateIsuesStatusResponseCall(@Header("Content-Type") String type, @Body UpdateIsuesStatusRequest updateIsuesStatusRequest);

    /*Update issues status cmrl*/
    @POST("tickethistory/update")
    Call<SuccessResponse> UpdateIsuesStatusResponseCMRLCall(@Header("Content-Type") String type, @Body UpdateIsuesStatusRequest updateIsuesStatusRequest);

    /*view tickets*/
    @POST("tickethistory/getlist_by_ticket_no")
    Call<ViewTicketsResponse> ViewTicketsResponseCall(@Header("Content-Type") String type, @Body ViewTicketsRequest viewTicketsRequest);


    /*check attendance*/
    @POST("attendance/check_attendance")
    Call<CheckAttendanceResponse> CheckAttendanceRequestCall(@Header("Content-Type") String type, @Body CheckAttendanceRequest checkAttendanceRequest);


    /*check attendance*/
    @POST("attendance/create")
    Call<SuccessResponse> AttendanceCreateRequestCall(@Header("Content-Type") String type, @Body AttendanceCreateRequest attendanceCreateRequest);


    /*cmrl_dashboard_count*/
    @POST("ticket/cmrl_dashboard_count")
    Call<CmrlDashboardCountResponse> CmrlDashboardCountRequestCall(@Header("Content-Type") String type, @Body CmrlDashboardCountRequest cmrlDashboardCountRequest);

   /*johnson_dashboard_count*/
    @POST("ticket/johnson_dashboard_count")
    Call<CmrlDashboardCountResponse> JohnsonDashboardCountRequestCall(@Header("Content-Type") String type, @Body CmrlDashboardCountRequest cmrlDashboardCountRequest);


    @Multipart
    @POST("upload")
    Call<ImageUploadResponse> getImageStroeResponse(@Part MultipartBody.Part file);

    /*elivators job_no list*/
    @GET("job_no/get_jobno/elivator")
    Call<JobNumberResponse> elivatorJobNoListResponseCall(@Header("Content-Type") String type);

    /*lift job_no list*/
    @GET("job_no/get_jobno/lift")
    Call<JobNumberResponse> liftJobNoListResponseCall(@Header("Content-Type") String type);


    /*Notification token update*/
    @POST("userdetails/mobile/update/fb_token")
    Call<FBTokenUpdateResponse> fBTokenUpdateResponseCall(@Header("Content-Type") String type, @Body FBTokenUpdateRequest fbTokenUpdateRequest);


    /*session clear*/
    @POST("userdetails/logout")
    Call<SuccessResponse> logoutResponseCall(@Header("Content-Type") String type, @Body LogoutRequest logoutRequest);


    //get Part List

    @POST("part_no/getlist_by_parttype")
    Call<GetPartListResponse> PartListCall(@Header("Content-Type") String type, @Body GetPartListRequest getPartListRequest);
}
