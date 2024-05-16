package uz.app.hotel.service;

public interface AdminService {
    void service();
    void addHotel();
    void showHotel();
    void showHotels();
    void editHotel();
    void deleteHotel();
    void showUsers();
    void showReservationHistory();
    void calcelReservation();
    void reserveForUser();

//    private static AdminService adminService ;
//    public static AdminService getInstance(){
//        if (adminService == null)
//            adminService=new AdminService();
//        return adminService;
//    }

}
