package com.example.yunseung_u.evnetbus;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.Subscribe;

public class UserFragment extends Fragment{
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Register the evnet to subscribe.
        GlobalBus.getBus().register(this);
        return inflater.inflate(R.layout.fragment_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setClickListener(view);
    }

    private void setClickListener(final View view) {
        Button btnSubmit = (Button) view.findViewById(R.id.submit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Person person = new Person();
                EditText etMessage = (EditText) view.findViewById(R.id.editText);

                person.setId(String.valueOf(etMessage.getText()));
                person.setName(String.valueOf(etMessage.getText()) + "HOOO");

                //We are broadingcasting the message here to listen to the subscriber.
                Events.FragmentActivityMessage fragmentActivityMessageEvent =
                        new Events.FragmentActivityMessage(
                               person
                        );
                GlobalBus.getBus().post(fragmentActivityMessageEvent);

            }
        });

    }

    @Subscribe
    public void getMessage(Events.ActivityFragmentMessage activityFragmentMessage){
        //write code to perform
        TextView messageView = (TextView) getView().findViewById(R.id.message);
        messageView.setText(
                getString(R.string.message_received) +
                        " " + activityFragmentMessage.getObject().getId());

        Toast.makeText(getActivity(),
                getString(R.string.message_fragment) +
                        " " + activityFragmentMessage.getObject().getId(),
                Toast.LENGTH_SHORT).show();

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Unregister the registered event.
        GlobalBus.getBus().unregister(this);
    }


}
