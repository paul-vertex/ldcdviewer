/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  com.google.gson.annotations.Expose
 */
package de.sbe.ldc.domain;

import com.google.gson.annotations.Expose;
import de.sbe.ldc.domain.AbstractBean;
import de.sbe.ldc.domain.Switch;

public class HostFlags
extends AbstractBean {
    public static final String PROPERTYNAME_INPUT = "input";
    public static final String PROPERTYNAME_INTERNET = "internet";
    public static final String PROPERTYNAME_INTRANET = "intranet";
    public static final String PROPERTYNAME_PRINTER = "printer";
    public static final String PROPERTYNAME_SCREEN = "screen";
    public static final String PROPERTYNAME_SWAP = "swap";
    public static final String PROPERTYNAME_USB = "usb";
    public static final String PROPERTYNAME_WEBFILTER = "webfilter";
    private static final long serialVersionUID = 3797876328791792842L;
    @Expose
    private Switch input;
    @Expose
    private Switch internet;
    @Expose
    private Switch intranet;
    @Expose
    private Switch printer;
    @Expose
    private Switch screen;
    @Expose
    private Switch swap;
    @Expose
    private Switch usb;
    @Expose
    private Switch webfilter;

    public Switch getInput() {
        return this.input;
    }

    public Switch getInternet() {
        return this.internet;
    }

    public Switch getIntranet() {
        return this.intranet;
    }

    public Switch getPrinter() {
        return this.printer;
    }

    public Switch getScreen() {
        return this.screen;
    }

    public Switch getSwap() {
        return this.swap;
    }

    public Switch getUsb() {
        return this.usb;
    }

    public Switch getWebfilter() {
        return this.webfilter;
    }

    public void setInput(Switch _input) {
        Switch old = this.input;
        this.input = _input;
        this.firePropertyChange(PROPERTYNAME_INPUT, (Object)old, (Object)this.input);
    }

    public void setInternet(Switch _internet) {
        Switch old = this.internet;
        this.internet = _internet;
        this.firePropertyChange(PROPERTYNAME_INTERNET, (Object)old, (Object)this.internet);
    }

    public void setIntranet(Switch _intranet) {
        Switch old = this.intranet;
        this.intranet = _intranet;
        this.firePropertyChange(PROPERTYNAME_INTRANET, (Object)old, (Object)this.intranet);
    }

    public void setPrinter(Switch _printer) {
        Switch old = this.printer;
        this.printer = _printer;
        this.firePropertyChange(PROPERTYNAME_PRINTER, (Object)old, (Object)this.printer);
    }

    public void setScreen(Switch _screen) {
        Switch old = this.screen;
        this.screen = _screen;
        this.firePropertyChange(PROPERTYNAME_SCREEN, (Object)old, (Object)this.screen);
    }

    public void setSwap(Switch _swap) {
        Switch old = this.swap;
        this.swap = _swap;
        this.firePropertyChange(PROPERTYNAME_SWAP, (Object)old, (Object)this.swap);
    }

    public void setUsb(Switch _usb) {
        Switch old = this.usb;
        this.usb = _usb;
        this.firePropertyChange(PROPERTYNAME_USB, (Object)old, (Object)this.usb);
    }

    public void setWebfilter(Switch _webfilter) {
        Switch old = this.webfilter;
        this.webfilter = _webfilter;
        this.firePropertyChange(PROPERTYNAME_WEBFILTER, (Object)old, (Object)this.webfilter);
    }
}

