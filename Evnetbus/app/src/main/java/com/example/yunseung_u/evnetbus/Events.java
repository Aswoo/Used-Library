package com.example.yunseung_u.evnetbus;

public class Events{


    //Evend used to send message from fragment to activity
    public static class FragmentActivityMessage{
        private String message;
        private Person person;
        public FragmentActivityMessage(String message) {
            this.message = message;
        }
        public FragmentActivityMessage(Person person){
            this.person = person;
        }
        public Person getObejct(){
            return person;
        }
        public String getMessage(){
            return message;
        }
    }
    //Event used to send message from activity to fragment

    public static class ActivityFragmentMessage{
        private String message;
        Person person;
        public ActivityFragmentMessage(String message){
            this.message = message;
        }
        public String getMessage(){
            return message;
        }
        public ActivityFragmentMessage(Person person){
           this.person = person;
        }
        public Person getObject(){
            return person;
        }
    }
    public static class ActivityAcitivtyMessage{
        private String message;
        public ActivityAcitivtyMessage(String message) {
            this.message = message;
        }
        public String getMessage(){
            return message;
        }
    }
}
