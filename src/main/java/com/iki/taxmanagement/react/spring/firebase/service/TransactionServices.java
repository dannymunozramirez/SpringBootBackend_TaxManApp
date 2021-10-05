package com.iki.taxmanagement.react.spring.firebase.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import com.iki.taxmanagement.react.spring.model.Transaction;

/**
 * 
 * @author dannymunoz
 *
 */
@Service
public class TransactionServices {

    public static final String FIREBASE_COLLECTION_NAME = "transactions";

    /**
     * <p>
     * This method is creating object into the firebase database.
     * </p>
     * 
     * @param transaction
     * @return
     * @throws InterruptedException
     * @throws ExecutionException
     */
    public String saveTransaction(Transaction transaction) throws InterruptedException, ExecutionException {

        Firestore dbFirestore = FirestoreClient.getFirestore();

        Iterable<DocumentReference> documentReference2 = dbFirestore.collection(FIREBASE_COLLECTION_NAME)
                .listDocuments();

        try {
            for (DocumentReference doc : documentReference2) {
                ApiFuture<DocumentSnapshot> future = doc.get();

                DocumentSnapshot document = future.get();

                System.out.println("DOCUMENT ID FROM FIRESTORE " + document.getId());
                System.out.println("DOCUMENT ID FROM REACT " + transaction.document_id);
                int id = Integer.parseInt(transaction.document_id);
                if (transaction.document_id.equals(document.getId())) {
                    id += 1;
                    System.out.println("TRANSACTION ID PLUS ONE " + id);
                    transaction.document_id = Integer.toString(id);

                }
            }

            System.out.println("DOCUMENT ID TO BE SET TO FIRESTORE " + transaction.document_id);
            ApiFuture<WriteResult> setTransaction = dbFirestore.collection(FIREBASE_COLLECTION_NAME)
                    .document(transaction.document_id).set(transaction);

            return setTransaction.get().getUpdateTime().toString();
        }

        catch (InterruptedException | ExecutionException e) {

            e.printStackTrace();
        }

        return null;

    }

    /**
     * <p>
     * This method is getting all the object into the firebase database.
     * </p>
     * 
     * @return
     * @throws InterruptedException
     * @throws ExecutionException
     */
    public List<Transaction> getTransactionDetails() throws InterruptedException, ExecutionException {

        // Map<String, Transaction> transaction = new HashMap<>();
        List<Transaction> transactions = new ArrayList<>();

        Firestore dbFirestore = FirestoreClient.getFirestore();

        Iterable<DocumentReference> documentReference2 = dbFirestore.collection(FIREBASE_COLLECTION_NAME)
                .listDocuments();

        for (DocumentReference doc : documentReference2) {
            try {
                ApiFuture<DocumentSnapshot> future = doc.get();

                DocumentSnapshot document = future.get();

                if (document.exists()) {

                    transactions.add(document.toObject(Transaction.class));

                }
                else {
                    return null;
                }

            }
            catch (InterruptedException | ExecutionException e) {

                e.printStackTrace();
            }
        }

        return transactions;

    }

    /**
     * <p>
     * This method is getting all the object into the firebase database.
     * </p>
     * 
     * @return
     * @throws InterruptedException
     * @throws ExecutionException
     */
    public Transaction getTransactionDetailsById(String document_id) throws InterruptedException, ExecutionException {

        // Map<String, Transaction> transaction = new HashMap<>();

        String document_idToString = document_id;

        Firestore dbFirestore = FirestoreClient.getFirestore();

        Iterable<DocumentReference> documentReference2 = dbFirestore.collection(FIREBASE_COLLECTION_NAME)
                .listDocuments();

        boolean found = false;
        String undefined = "undefined";

        while (!found) {
            if (document_idToString.equals(undefined)) {
                found = true;
            }
            for (DocumentReference doc : documentReference2) {
                try {
                    ApiFuture<DocumentSnapshot> future = doc.get();

                    DocumentSnapshot document = future.get();

                    if (document.exists()) {

                        if (document.getId().equals(document_idToString)) {

                            found = true;
                            return (document.toObject(Transaction.class));

                        }

                    }
                    else {
                        return null;
                    }

                }
                catch (InterruptedException | ExecutionException e) {

                    e.printStackTrace();
                }

            }
        }

        return null;

    }

    /**
     * 
     * @param document_id
     * @return
     */
    public String deleteTransaction(String document_id) {

        Firestore dbFirestore = FirestoreClient.getFirestore();
        dbFirestore.collection(FIREBASE_COLLECTION_NAME).document(document_id).delete();
        return "Document with Transaction ID " + document_id + " has been deleted";

    }

    /**
     * 
     * @param transaction
     * @return
     * @throws InterruptedException
     * @throws ExecutionException
     */
    public String updateTransactionDetails(Transaction transaction) throws InterruptedException, ExecutionException {

        System.out.println("DOCUMENT ID: " + transaction.document_id);

        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> collectionsApiFuture = dbFirestore.collection(FIREBASE_COLLECTION_NAME)
                .document(transaction.document_id).set(transaction);
        return collectionsApiFuture.get().getUpdateTime().toString();

    }

}
