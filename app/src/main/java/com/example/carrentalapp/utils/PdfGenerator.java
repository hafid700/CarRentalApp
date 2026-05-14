package com.example.carrentalapp.utils;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;
import android.content.Intent;
import androidx.core.content.FileProvider;
import android.net.Uri;

import com.example.carrentalapp.data.entity.*;

import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import java.io.File;

public class PdfGenerator {

    public static void generate(Context context,
                                Reservation reservation,
                                Client client,
                                Voiture voiture) {

        try {

            // ✅ STOCKAGE COMPATIBLE ANDROID MODERNE
            File dir = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);

            if (dir == null) {
                Toast.makeText(context, "Stockage indisponible", Toast.LENGTH_SHORT).show();
                return;
            }

            File file = new File(dir, "contrat_" + reservation.getId() + ".pdf");

            PdfWriter writer = new PdfWriter(file);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            document.add(new Paragraph("CONTRAT DE LOCATION"));
            document.add(new Paragraph("----------------------------"));

            document.add(new Paragraph("Client : " + client.getNom() + " " + client.getPrenom()));
            document.add(new Paragraph("Email : " + client.getEmail()));
            document.add(new Paragraph("Téléphone : " + client.getTelephone()));

            document.add(new Paragraph(" "));

            document.add(new Paragraph("Voiture : " + voiture.getMarque() + " " + voiture.getModele()));
            document.add(new Paragraph("ID voiture : " + voiture.getId()));
            document.add(new Paragraph("Prix/jour : " + voiture.getPrix_jour() + " DH"));

            document.add(new Paragraph(" "));

            document.add(new Paragraph("Date début : " + reservation.getDate_debut()));
            document.add(new Paragraph("Date fin : " + reservation.getDate_fin()));

            document.add(new Paragraph("Prix total : " + reservation.getPrix_total() + " DH"));

            document.add(new Paragraph(" "));
            document.add(new Paragraph("Signature client: __________________"));

            document.close();

            Uri uri = FileProvider.getUriForFile(
                    context,
                    context.getPackageName() + ".provider",
                    file
            );

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(uri, "application/pdf");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            context.startActivity(intent);

            Toast.makeText(context,
                    "PDF généré : " + file.getAbsolutePath(),
                    Toast.LENGTH_LONG).show();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context,
                    "Erreur PDF: " + e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }
}