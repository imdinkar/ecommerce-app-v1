package com.example.ecommerce.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;

@Controller
public class ImageController {
    
    @GetMapping("/images/placeholder/{width}x{height}")
    public ResponseEntity<byte[]> generatePlaceholder(
            @PathVariable int width, 
            @PathVariable int height,
            @RequestParam(defaultValue = "667eea") String color,
            @RequestParam(defaultValue = "ffffff") String textColor,
            @RequestParam(defaultValue = "Product") String text) {
        
        try {
            // Create buffered image
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = image.createGraphics();
            
            // Set rendering hints for better quality
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            
            // Parse colors
            Color bgColor = Color.decode("#" + color);
            Color fgColor = Color.decode("#" + textColor);
            
            // Fill background
            g2d.setColor(bgColor);
            g2d.fillRect(0, 0, width, height);
            
            // Draw text
            g2d.setColor(fgColor);
            Font font = new Font("Arial", Font.BOLD, Math.min(width, height) / 8);
            g2d.setFont(font);
            
            FontMetrics fm = g2d.getFontMetrics();
            int textWidth = fm.stringWidth(text);
            int textHeight = fm.getHeight();
            
            int x = (width - textWidth) / 2;
            int y = (height - textHeight) / 2 + fm.getAscent();
            
            g2d.drawString(text, x, y);
            g2d.dispose();
            
            // Convert to byte array
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "PNG", baos);
            byte[] imageBytes = baos.toByteArray();
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);
            headers.setContentLength(imageBytes.length);
            headers.setCacheControl("public, max-age=31536000"); // Cache for 1 year
            
            return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
            
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
