package com.cob.emr.usecases.signup.creator;


import com.cob.emr.exception.business.UserException;
import com.cob.emr.model.organization.OrganizationModel;
import com.cob.emr.model.organization.OrganizationRequest;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public abstract class OrganizationRequestCreator {
    private OrganizationRequestCreator next;

    public static OrganizationRequestCreator link(OrganizationRequestCreator first, OrganizationRequestCreator... chain) {
        OrganizationRequestCreator head = first;
        for (OrganizationRequestCreator nextInChain : chain) {
            head.next = nextInChain;
            head = nextInChain;
        }
        return first;
    }

    public abstract boolean create(OrganizationRequest model) throws NoSuchPaddingException, UnsupportedEncodingException, IllegalBlockSizeException, UserException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException;
    protected boolean nextStep(OrganizationRequest model) throws NoSuchPaddingException, UnsupportedEncodingException, IllegalBlockSizeException, UserException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        if (next == null) {
            return true;
        }
        return next.create(model);
    }
}
