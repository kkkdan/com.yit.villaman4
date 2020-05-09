package permission;
 ;

import java.util.ArrayList;

public class PermissionResult {

  private boolean granted;
  private ArrayList<String> deniedPermissions;

  public PermissionResult(ArrayList<String> deniedPermissions) {
    this.granted = ObjectUtils.isEmpty(deniedPermissions);
    this.deniedPermissions = deniedPermissions;
  }

  public boolean isGranted() {
    return granted;
  }

  public ArrayList<String> getDeniedPermissions() {
    return deniedPermissions;
  }
}
