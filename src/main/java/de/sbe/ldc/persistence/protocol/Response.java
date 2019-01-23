/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.ldc.persistence.protocol;

import de.sbe.ldc.persistence.protocol.Processor;
import de.sbe.ldc.persistence.protocol.ProcessorAdapter;
import de.sbe.ldc.persistence.protocol.Request;
import de.sbe.utils.StringUtils;
import de.sbe.utils.logging.LogUtils;
import java.util.Scanner;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Response {
    private static final Pattern DATA_NUMBER_PATTERN = Pattern.compile("\\([0-9]+.+\\):$");
    private static final Pattern ID_PATTERN = Pattern.compile(".+?:\\s*([0-9]+)$");
    private static final Pattern STATUS_PATTERN = Pattern.compile("(-|\\+)(err|ok)(\\s+\\[enoperm\\])?", 2);
    private final Logger logger = LogUtils.getLogger(this.getClass());
    private final Processor processor;
    private final Request request;

    public Response(Request _request) {
        this(_request, null);
    }

    public Response(Request _request, Processor _processor) {
        this.request = _request;
        this.processor = _processor != null ? _processor : new ProcessorAdapter();
    }

    public Processor getProcessor() {
        return this.processor;
    }

    public Request getRequest() {
        return this.request;
    }

    public boolean isFailed() {
        return !Request.RequestStatus.OK.equals((Object)this.request.getStatus());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void processHeader(String _header) {
        this.logger.finest(_header);
        if (StringUtils.isEmptyString(_header)) {
            return;
        }
        Scanner scanner = null;
        try {
            String number;
            Matcher matcher;
            String status;
            scanner = new Scanner(_header);
            status = scanner.findInLine(STATUS_PATTERN);
            this.request.setStatus(status);
            number = scanner.findInLine(DATA_NUMBER_PATTERN);
            if (!StringUtils.isEmptyString(number)) {
                Scanner numberScanner = null;
                try {
                    numberScanner = new Scanner(number.substring(1, number.length() - 2));
                    this.request.setDataNumber(numberScanner.hasNextInt() ? numberScanner.nextInt() : -1);
                }
                finally {
                    if (numberScanner != null) {
                        numberScanner.close();
                    }
                }
            }
            if ((matcher = ID_PATTERN.matcher(_header)).matches()) {
                this.getProcessor().setId(Long.parseLong(matcher.group(1)));
            }
            String info = _header;
            if (!StringUtils.isEmptyString(status)) {
                info = info.substring(status.length());
            }
            if (!StringUtils.isEmptyString(number)) {
                info = info.substring(0, info.length() - number.length());
            }
            if (info.endsWith(":")) {
                info = info.substring(0, info.length() - 1);
            }
            this.request.setInfo(info.trim());
        }
        finally {
            if (scanner != null) {
                scanner.close();
            }
        }
    }
}

