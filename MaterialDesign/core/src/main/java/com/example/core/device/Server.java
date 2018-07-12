package com.example.core.device;


import com.example.core.utils.Tools;

public class Server {

    private String address;
    private Integer port = null;

    public Server() {
    }

    public Server(String address, int port) {
        this.address = address;
        this.port = port;
    }

    public Server(String address, String port) {
        this.address = address;
        if (!Tools.isEmpty(port)) {
            try {
                this.port = Integer.parseInt(port);
            } catch (Exception e) {
            }
        }
    }

    public Server(String url) {
        String[] array = url.split(":");
        address = array[0];
        if (array.length > 1) {
            try {
                port = Integer.parseInt(array[1]);
            } catch (Exception e) {
            }
        }
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getPort() {
        if (port != null) {
            return port;
        }
        return 80;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUrl() {
        if (this.address == null) {
            return null;
        }
        if (port != null) {
            return address + ":" + port;
        }
        return this.address;
    }

}
