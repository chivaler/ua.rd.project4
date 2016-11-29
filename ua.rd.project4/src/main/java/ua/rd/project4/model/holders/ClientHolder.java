package ua.rd.project4.model.holders;

import ua.rd.project4.domain.Client;

public class ClientHolder extends Client{
    public ClientHolder(String firstName, String lastName, String address, String telephone, String email, String idCard) {
        super(firstName, lastName, address, telephone, email, idCard);
    }
}
