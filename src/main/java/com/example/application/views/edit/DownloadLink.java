package com.example.application.views.edit;


import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.server.StreamResource;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class DownloadLink extends Anchor {
    public DownloadLink(File file) {
        Anchor anchor = new Anchor(getStreamResource(file.getName(), file), file.getName());
        anchor.getElement().setAttribute("download", true);
        anchor.setHref(getStreamResource(file.getName(), file));
        add(anchor);
    }
    public StreamResource getStreamResource(String filename, File content) {
        return new StreamResource(filename, () -> {
            try {
                return new BufferedInputStream(new FileInputStream(content));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        });
    }
}
