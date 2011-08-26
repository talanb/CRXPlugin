package com.meredith.devtools.intellij.crx.vfs;

import org.apache.jackrabbit.value.BinaryValue;

import javax.jcr.*;
import javax.jcr.lock.LockException;
import javax.jcr.nodetype.ConstraintViolationException;
import javax.jcr.nodetype.NoSuchNodeTypeException;
import javax.jcr.version.VersionException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class BinaryDataOutputStream extends OutputStream {
    private Property property;
    private List<Integer> bytes;
    private boolean dirty;

    public BinaryDataOutputStream(Property property) {
        this.property = property;
        bytes = new ArrayList<Integer>();
        dirty = false;
    }

    @Override
    public void flush() throws IOException {
        saveBytes(intListToBytes(bytes));
        dirty=false;
        super.flush();
    }

    private byte[] intListToBytes(List<Integer> bytes) {
        byte [] ba = new byte[bytes.size()];
        for (int i = 0; i < bytes.size(); i++) {
            ba[i] = bytes.get(i).byteValue();
        }
        return ba;
    }

    @Override
    public void close() throws IOException {
        saveBytes(intListToBytes(bytes));
        dirty=false;
        super.close();
    }

    @Override
    public void write(int b) throws IOException {
        bytes.add(b);
        dirty = true;
    }

    @Override
    public void write(byte[] b) throws IOException {
        saveBytes(b);
    }

    private void saveBytes(byte[] b) {
        try {
            BinaryValue value = new BinaryValue(b);
            property.setValue(value);
            property.getParent().getSession().save();
        } catch (ItemExistsException e) {
            e.printStackTrace();
        } catch (AccessDeniedException e) {
            e.printStackTrace();
        } catch (ItemNotFoundException e) {
            e.printStackTrace();
        } catch (InvalidItemStateException e) {
            e.printStackTrace();
        } catch (ReferentialIntegrityException e) {
            e.printStackTrace();
        } catch (NoSuchNodeTypeException e) {
            e.printStackTrace();
        } catch (ValueFormatException e) {
            e.printStackTrace();
        } catch (VersionException e) {
            e.printStackTrace();
        } catch (ConstraintViolationException e) {
            e.printStackTrace();
        } catch (LockException e) {
            e.printStackTrace();
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
    }

}
