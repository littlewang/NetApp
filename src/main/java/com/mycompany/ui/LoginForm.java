package com.mycompany.ui;

import com.mycompany.app.Login;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by administrator on 14-6-15.
 */




public class LoginForm extends JFrame {

    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
    JLabel label1 = new JLabel("姓名");
    JLabel label2 = new JLabel("密码");
    final JTextField text1 = new JTextField();
    final JPasswordField text2 = new JPasswordField();
    JButton button = new JButton("Login");
    LoginForm(){
        super("登陆");
        setBounds(((int)dimension.getWidth() - 200) / 2, ((int)dimension.getHeight() - 300) / 2, 200, 150);
        setResizable(false);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        label1.setBounds(10, 10, 100, 30);
        add(label1);
        label2.setBounds(10, 40, 100, 30);
        add(label2);

        text1.setBounds(50, 15, 130, 20);
        add(text1);

        text2.setBounds(50, 45, 130, 20);
        add(text2);
        button.setBounds(10, 75, 170, 40);
        add(button);
        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                String name=text1.getText();

                String pass= String.valueOf(text2.getPassword());


                Login lo = new Login();
                CloseableHttpClient client = HttpClients.createDefault();

                lo.login(client,name,pass);

                System.out.println(this);




            }


        });


        setVisible(true);

    }



}
