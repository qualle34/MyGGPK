package incorporated.qualle.myggpk.group;

import java.util.ArrayList;
import java.util.List;

public class GroupFabric {

    private final static List<Group> groupList = new ArrayList<>();

    static {
        groupList.add(0, new Group(0, "init", null, null));
        groupList.add(1, new Group(1, "First", "http://www.ggpk.by/Raspisanie/Files/P_KURS.html", "http://www.ggpk.by/Raspisanie/Files/P_KURS.html"));
        groupList.add(2, new Group(2, "AGB", "http://www.ggpk.by/Raspisanie/Files/AGB.html", "http://www.ggpk.by/Raspisanie/Files/PTOO.html"));
        groupList.add(3, new Group(3, "TOR", "http://www.ggpk.by/Raspisanie/Files/TAR.html", "http://www.ggpk.by/Raspisanie/Files/MRSO.html"));
        groupList.add(4, new Group(4, "PGB", "http://www.ggpk.by/Raspisanie/Files/PGB.html", "http://www.ggpk.by/Raspisanie/Files/PGS.html"));
        groupList.add(5, new Group(5, "BDA", "http://www.ggpk.by/Raspisanie/Files/BDA.html", "http://www.ggpk.by/Raspisanie/Files/PGS.html"));
        groupList.add(6, new Group(6, "VMS", "http://www.ggpk.by/Raspisanie/Files/VMS.html", "http://www.ggpk.by/Raspisanie/Files/VMSO.html"));
        groupList.add(7, new Group(7, "PZT", "http://www.ggpk.by/Raspisanie/Files/PZT.html", "http://www.ggpk.by/Raspisanie/Files/VMSO.html"));
        groupList.add(8, new Group(8, "AEP", "http://www.ggpk.by/Raspisanie/Files/AEP.html", "http://www.ggpk.by/Raspisanie/Files/VMSO.html"));
        groupList.add(9, new Group(9, "BSK", "http://www.ggpk.by/Raspisanie/Files/BSB.html", "http://www.ggpk.by/Raspisanie/Files/VMSO.html"));
        groupList.add(10, new Group(10, "PTO", "http://www.ggpk.by/Raspisanie/Files/PTO.html", "http://www.ggpk.by/Raspisanie/Files/PTOO.html"));
    }

    public static Group getGroup(int id) {
        return groupList.get(id);
    }

    public static List<Group> getGroupList() {
        return groupList;
    }
}