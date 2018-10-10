package uk.ac.st_andrews.cs.mamoc_client;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import uk.ac.st_andrews.cs.mamoc_client.Communication.DataSender;
import uk.ac.st_andrews.cs.mamoc_client.Model.MamocNode;

public class DialogUtils {

    public static final int CODE_PICK_IMAGE = 21;

    public static AlertDialog getServiceSelectionDialog(final Activity activity, final MamocNode
            selectedDevice) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);
        alertDialog.setTitle(selectedDevice.getNodeName());
        String[] types = {"Send image", "Request"};
        alertDialog.setItems(types, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
                switch (which) {
                    case 0:
                        Intent imagePicker = new Intent(Intent.ACTION_PICK);
                        imagePicker.setType("image/*");
                        activity.startActivityForResult(imagePicker, CODE_PICK_IMAGE);
                        break;
                    case 1:
                        DataSender.sendChatRequest(activity, selectedDevice.getIp
                                (), selectedDevice.getPort());
                        Toast.makeText(activity, "chat request sent", Toast.LENGTH_SHORT).show();
                        break;
                }
            }

        });

        return (alertDialog.create());
    }

    public static AlertDialog getChatRequestDialog(final Activity activity, final MamocNode requesterDevice) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);

        String chatRequestTitle = activity.getString(R.string.chat_request_title);
        chatRequestTitle = String.format(chatRequestTitle, requesterDevice.getNodeName() + "(" +
                requesterDevice.getNodeName() + ")");
        alertDialog.setTitle(chatRequestTitle);
        String[] types = {"Accept", "Reject"};
        alertDialog.setItems(types, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
                switch (which) {
                    //Request accepted
                    case 0:
                        openChatActivity(activity, requesterDevice);
                        Toast.makeText(activity, "Request Accepted", Toast.LENGTH_SHORT).show();
                        DataSender.sendChatResponse(activity, requesterDevice.getIp(),
                                requesterDevice.getPort(), true);
                        break;
                    // Request rejected
                    case 1:
                        DataSender.sendChatResponse(activity, requesterDevice.getIp(),
                                requesterDevice.getPort(), false);
                        Toast.makeText(activity, "Request Rejected", Toast.LENGTH_SHORT).show();

                        break;
                }
            }

        });

        return (alertDialog.create());
    }

    public static void openChatActivity(Activity activity, MamocNode device) {
//        Intent chatIntent = new Intent(activity, ChatActivity.class);
//        chatIntent.putExtra(ChatActivity.KEY_CHAT_IP, device.getIp());
//        chatIntent.putExtra(ChatActivity.KEY_CHAT_PORT, device.getPort());
//        chatIntent.putExtra(ChatActivity.KEY_CHATTING_WITH, device.getPlayerName());
//        activity.startActivity(chatIntent);
        Log.v("CHAT", "open chat activity");
    }
}