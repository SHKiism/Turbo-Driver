package ir.transport_x.taxi.model;


public class RegisterModel {

  String title;
  String message;
  String station;

  public String getTitle() {
    return this.title;
  }

 public String getStation() {
    return this.station;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getMessage() {
    return this.message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
 public void setStation(String station) {
    this.station = station;
  }

}
