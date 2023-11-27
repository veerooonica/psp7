package app;

import javax.swing.JButton;
import java.awt.*;
import java.awt.event.*;

public class ClientOper extends Frame {
    private boolean isRunning = true;
    public static void main(String[] args) {
        ClientOper window = new ClientOper();
        window.run();
    }
    public void run() {
        setVisible(true);
        while (isRunning) {
            // Здесь можно вставить код, но все все равно обрабатывается через события
        }
    }
    public ClientOper() {
        setTitle("Окно выбора операций");
        setSize(500, 400);
        setLayout(new FlowLayout());
        Button addButton = new Button("Добавить");
        Button editButton = new Button("Изменить");
        Button deleteButton = new Button("Удалить");
        JButton searchNameButton = new JButton("Поиск по фамилии");
        JButton searchDolgnostButton = new JButton("Поиск по должности");
        Button exitButton = new Button("Завершить работу");
        addButton.addActionListener(add);
        editButton.addActionListener(change);
        deleteButton.addActionListener(delete);
        searchNameButton.addActionListener(searchName);
        searchDolgnostButton.addActionListener(searchDolgnost);
        exitButton.addActionListener(exit);
        add(addButton);
        add(editButton);
        add(deleteButton);
        add(searchNameButton);
        add(searchDolgnostButton);
        add(exitButton);
        Color backgroundColor = new Color(235, 201, 250);
        setBackground(backgroundColor);
    }
    ActionListener add = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ae) {
            SystWind c = new SystWind();
            boolean isChange = false;
            c.addNoteForm(isChange);
        }
    };
    ActionListener change = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ae) {
            SystWind c = new SystWind();
            boolean isChange = true;
            c.deleteNoteForm(isChange);
        }
    };
    ActionListener delete = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ae) {
            SystWind c = new SystWind();
            boolean isChange = false;
            c.deleteNoteForm(isChange);
        }
    };
    ActionListener select = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ae) {
            SystWind c = new SystWind();
            c.displayStudentData(null, null);
        }
    };
    ActionListener searchName = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ae) {
            SystWind c = new SystWind();
            c.searchForm(true);
        }
    };
    ActionListener searchDolgnost = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ae) {
            SystWind c = new SystWind();
            c.searchForm(false);
        }
    };
    ActionListener exit = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ae) {
            isRunning = false;
            System.exit(0);
        }
    };
}
