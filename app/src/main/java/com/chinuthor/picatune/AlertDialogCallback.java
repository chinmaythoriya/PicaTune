package com.chinuthor.picatune;

import android.content.DialogInterface;

public interface AlertDialogCallback {

    void onPositiveButtonClick(DialogInterface dialog);

    void onNegativeButtonClick(DialogInterface dialog);

}
