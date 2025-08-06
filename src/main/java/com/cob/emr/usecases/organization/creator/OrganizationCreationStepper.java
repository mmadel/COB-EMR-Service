package com.cob.emr.usecases.organization.creator;

import com.cob.emr.exception.business.UserException;
import com.cob.emr.model.organization.OrganizationModel;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public abstract class OrganizationCreationStepper {
    private OrganizationCreationStepper next;
    public static OrganizationCreationStepper link(OrganizationCreationStepper first, OrganizationCreationStepper... chain) {
        OrganizationCreationStepper head = first;
        for (OrganizationCreationStepper nextInChain: chain) {
            head.next = nextInChain;
            head = nextInChain;
        }
        return first;
    }

    public abstract boolean create(OrganizationModel model) throws NoSuchPaddingException, UnsupportedEncodingException, IllegalBlockSizeException, UserException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException;

    protected boolean nextStep(OrganizationModel model) throws NoSuchPaddingException, UnsupportedEncodingException, IllegalBlockSizeException, UserException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        if (next == null) {
            return true;
        }
        return next.create(model);
    }
}
