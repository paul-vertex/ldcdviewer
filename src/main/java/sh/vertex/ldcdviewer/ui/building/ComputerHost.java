package sh.vertex.ldcdviewer.ui.building;

import java.util.ArrayList;
import java.util.List;

/**
 * Host Model, Contains all kind of different information
 *
 * created by paul on 2019-01-19 at 21:56
 */
public class ComputerHost {

    private String hostEnding;

    private boolean inExam;
    private boolean printerState;
    private boolean internetState;
    private boolean usbState;
    private boolean powerState;
    private boolean ssm;
    private long uptime;
    private boolean intranet;
    private long idleTime;
    private String agentVersion;
    private String osArch;
    private String ip;
    private String osName;
    private String osVersion;
    private List<String> users = new ArrayList<>();

    public String getHostEnding() {
        return hostEnding;
    }

    public void setHostEnding(String hostEnding) {
        this.hostEnding = hostEnding;
    }

    public boolean isInExam() {
        return inExam;
    }

    public void setInExam(boolean inExam) {
        this.inExam = inExam;
    }

    public boolean isPrinterState() {
        return printerState;
    }

    public void setPrinterState(boolean printerState) {
        this.printerState = printerState;
    }

    public boolean isInternetState() {
        return internetState;
    }

    public void setInternetState(boolean internetState) {
        this.internetState = internetState;
    }

    public boolean isUsbState() {
        return usbState;
    }

    public void setUsbState(boolean usbState) {
        this.usbState = usbState;
    }

    public boolean isPowerState() {
        return powerState;
    }

    public void setPowerState(boolean powerState) {
        this.powerState = powerState;
    }

    public boolean isSsm() {
        return ssm;
    }

    public void setSsm(boolean ssm) {
        this.ssm = ssm;
    }

    public long getUptime() {
        return uptime;
    }

    public void setUptime(long uptime) {
        this.uptime = uptime;
    }

    public boolean isIntranet() {
        return intranet;
    }

    public void setIntranet(boolean intranet) {
        this.intranet = intranet;
    }

    public long getIdleTime() {
        return idleTime;
    }

    public void setIdleTime(long idleTime) {
        this.idleTime = idleTime;
    }

    public String getAgentVersion() {
        return agentVersion;
    }

    public void setAgentVersion(String agentVersion) {
        this.agentVersion = agentVersion;
    }

    public String getOsArch() {
        return osArch;
    }

    public void setOsArch(String osArch) {
        this.osArch = osArch;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public List<String> getUsers() {
        return users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }

    public String getOsName() {
        return osName;
    }

    public void setOsName(String osName) {
        this.osName = osName;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }
}
