package com.example.core.device;

/**
 */
public class APN {
  private String name;
  private Server proxyServer;
  private String apnType;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Server getProxyServer() {
    return proxyServer;
  }

  public void setProxyServer(Server proxyServer) {
    this.proxyServer = proxyServer;
  }

  public String getApnType() {
    return apnType;
  }

  public void setApnType(String apnType) {
    this.apnType = apnType;
  }

}
