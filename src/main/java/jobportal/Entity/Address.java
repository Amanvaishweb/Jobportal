package jobportal.Entity;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Embeddable
public class Address {
    @NotBlank(message="Street cannot be null/Empty or Blank")
    private String street;
    @NotBlank(message="city cannot be null/Empty or Blank")
    private String city;
    @NotBlank(message="state cannot be null/Empty or Blank")
    private String state;
    @Min(value = 100000, message = "Pincode must be 6 digits")
    @Max(value = 999999, message = "Pincode must be 6 digits")

    private int pincode;

    public Address(){}
    public Address(String street, String city, String state, int pincode) {
        this.street = street;
        this.city = city;
        this.state = state;
        this.pincode = pincode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getPincode() {
        return pincode;
    }

    public void setPincode(int pincode) {
        this.pincode = pincode;
    }
}
