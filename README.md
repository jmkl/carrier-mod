Carrier image mod
=================

How it work...??

just build the project.. then decompile the apk.

copy the in folder inside smali somewhere and open it into last dir.
delete all smali file except Carrier.smali and Carrier$1.smali

decompile ur systemUI then copy those in folder into systemui smali folder

open ur SystemUI/res/layout/status_bar.xml
and copy this line wherever u want to add the carrier image

        <in.jmkl.dcsms.carrierimage.Carrier
            android:adjustViewBounds="true"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content" />

build and push

install this project.apk
and enjoy ur custom carrier image

(Note : u need quickpic to run it. find it on playstore)
