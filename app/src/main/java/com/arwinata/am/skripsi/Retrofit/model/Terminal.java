package com.arwinata.am.skripsi.Retrofit.model;

public class Terminal {

    private String _id;
    private String namaTerminal;
    private String alamatTerminal;
    private String date;
    private String user;
    private String terminal;

    public Terminal(String terminal, String user, String date) {
        this.terminal = terminal;
        this.date = date;
        this.user = user;
    }

    public String get_id() {
        return _id;
    }

    public String getNamaTerminal() {
        return namaTerminal;
    }

    public String getAlamatTerminal() {
        return alamatTerminal;
    }
}
