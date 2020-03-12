package Core.Repair;

import Core.Common.SettingName;
import Core.Customer.Customer;


public class RepairBuilder {

    private Repair repair;

    public RepairBuilder(){
        repair = new Repair();
    }
    public void addCustomer(Customer c){
        if( c != null){
            Customer customer = Customer.clone(c);
            repair.setCustomer(customer);
        }else repair.setCustomer(null);
    }
    public void addCode(String code){
        repair.add(SettingName.REPAIRCODE, code);
    }
    public void addNumber(String number){
        repair.add(SettingName.REPAIRNUMBER, number);
    }
    public void addPickUpDate(String date){
        repair.add(SettingName.REPAIRPICKUPDATE, date);
    }
    public void addBrand(String brand){
        repair.add(SettingName.REPAIRBRAND, brand);
    }
    public void addModel(String model){
        repair.add(SettingName.REPAIRMODEL, model);
    }
    public void addSerialNumber(String serialNumber){
        repair.add(SettingName.REPAIRSERIALNUMBER, serialNumber);
    }
    public void addPurchuaseDate(String date){
        repair.add(SettingName.REPAIRPURCHUASEDATE, date);
    }
    public void addBrake(String brake){
        repair.add(SettingName.REPAIRBRAKE, brake);
    }
    public void addNote(String note){
        repair.add(SettingName.REPAIRNOTE, note);
    }
    public void addPrice(double price){
        repair.add(SettingName.REPAIRPRICE, Double.toString(price));
    }
    public void addPreventive(boolean preventive){ repair.add(SettingName.REPAIRPREVENTIVE, Boolean.toString(preventive)); }
    public Repair getResult(){
        return repair;
    }

}
