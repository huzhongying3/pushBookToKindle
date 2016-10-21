package com.pushbooktokindle;

/**
 * <一句话功能简述>
 * <功能详细描述>
 *
 * @auth:huzhongying
 * @see: [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import com.github.axet.wget.SpeedInfo;
import com.github.axet.wget.WGet;
import com.github.axet.wget.info.DownloadInfo;
import com.github.axet.wget.info.DownloadInfo.Part;
import com.github.axet.wget.info.DownloadInfo.Part.States;
import com.github.axet.wget.info.ex.DownloadMultipartError;

public class ApplicationManaged {

    AtomicBoolean stop = new AtomicBoolean(false);
    DownloadInfo info;
    long last;
    SpeedInfo speedInfo = new SpeedInfo();

    public static String formatSpeed(long s) {
        if (s > 0.1 * 1024 * 1024 * 1024) {
            float f = s / 1024f / 1024f / 1024f;
            return String.format("%.1f GB/s", f);
        } else if (s > 0.1 * 1024 * 1024) {
            float f = s / 1024f / 1024f;
            return String.format("%.1f MB/s", f);
        } else {
            float f = s / 1024f;
            return String.format("%.1f kb/s", f);
        }
    }

    public void downLoad(String pathname, String urlStr) {
        try {
            Runnable notify = new Runnable() {
                @Override
                public void run() {
                    // notify app or save download state
                    // you can extract information from DownloadInfo info;
                    switch (info.getState()) {
                        case EXTRACTING:
                        case EXTRACTING_DONE:
                            System.out.println(info.getState());
                            break;
                        case DONE:
                            // finish speed calculation by adding remaining bytes speed
                            speedInfo.end(info.getCount());
                            // print speed
                            System.out.println(String.format("%s average speed (%s)", info.getState(), formatSpeed(speedInfo.getAverageSpeed())));
                            break;
                        case RETRYING:
                            System.out.println(info.getState() + " " + info.getDelay());
                            break;
                        case DOWNLOADING:
                            speedInfo.step(info.getCount());
                            long now = System.currentTimeMillis();
                            if (now - 1000 > last) {
                                last = now;

                                String parts = "";

                                List<Part> infoParts = info.getParts();
                                if (infoParts != null) {
                                    for (Part p : infoParts) {
                                        if (p.getState().equals(States.DOWNLOADING)) {
                                            parts += String.format("Part#%d(%.2f) ", p.getNumber(),
                                                    p.getCount() / (float) p.getLength());
                                        }
                                    }

                                    float p = info.getCount() / (float) info.getLength();

                                    System.out.println(String.format("%.2f %s (%s / %s)", p, parts,
                                            formatSpeed(speedInfo.getCurrentSpeed()),
                                            formatSpeed(speedInfo.getAverageSpeed())));
                                }

                            }
                            break;
                        default:
                            break;
                    }
                }
            };

            // choice file
            URL url = new URL(urlStr);
            // initialize url information object with or without proxy
            // info = new DownloadInfo(url, new ProxyInfo("proxy_addr", 8080, "login", "password"));
            info = new DownloadInfo(url);
            // extract information from the web
            info.extract(stop, notify);
            // enable multipart download
            info.enableMultipart();
            // Choice target file or set download folder
            File target = new File(pathname);
            // create wget downloader
            WGet w = new WGet(info, target);
            // init speedinfo
            speedInfo.start(0);
            // will blocks until download finishes
            w.download(stop, notify);
        } catch (DownloadMultipartError e) {
            for (Part p : e.getInfo().getParts()) {
                Throwable ee = p.getException();
                if (ee != null)
                    ee.printStackTrace();
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}