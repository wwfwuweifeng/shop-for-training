package top.wwf.modules.user.dto;

/**
* @Description:    TODO
* @Author:         wwf（hitwh_wwf@163.com）
* @CreateDate:     2019-07-08 15:17
*/
public class UserInfoDTO {
    private String userSex;
    private String telNum;
    private String email;
    private String shopName;

    private String receiverName;
    private String receiverTel;
    private String receiverAddressSimple;
    private String receiverAddressDetail;
    private int isEditReceiverAddress;

    public UserInfoDTO() {
    }

    public UserInfoDTO(String userSex, String telNum, String email, String shopName, String receiverName, String receiverTel, String receiverAddressSimple,
            String receiverAddressDetail, int isEditReceiverAddress) {
        this.userSex = userSex;
        this.telNum = telNum;
        this.email = email;
        this.shopName = shopName;
        this.receiverName = receiverName;
        this.receiverTel = receiverTel;
        this.receiverAddressSimple = receiverAddressSimple;
        this.receiverAddressDetail = receiverAddressDetail;
        this.isEditReceiverAddress = isEditReceiverAddress;
    }

    public int getIsEditReceiverAddress() {
        return isEditReceiverAddress;
    }

    public void setIsEditReceiverAddress(int isEditReceiverAddress) {
        this.isEditReceiverAddress = isEditReceiverAddress;
    }

    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    public String getTelNum() {
        return telNum;
    }

    public void setTelNum(String telNum) {
        this.telNum = telNum;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverTel() {
        return receiverTel;
    }

    public void setReceiverTel(String receiverTel) {
        this.receiverTel = receiverTel;
    }

    public String getReceiverAddressSimple() {
        return receiverAddressSimple;
    }

    public void setReceiverAddressSimple(String receiverAddressSimple) {
        this.receiverAddressSimple = receiverAddressSimple;
    }

    public String getReceiverAddressDetail() {
        return receiverAddressDetail;
    }

    public void setReceiverAddressDetail(String receiverAddressDetail) {
        this.receiverAddressDetail = receiverAddressDetail;
    }
}
