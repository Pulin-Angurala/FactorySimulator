import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class FactorySimulatorGUI extends JPanel implements ActionListener{
    private final ConveyorBelt[] conveyorBelts = new ConveyorBelt[5];
    private MachineDispatcherPanel machineDispatcherPanel;
    private TogglePanel togglePanel;
    private JLabel header;
    private Timer timer;
    private ArrayList<Dispatcher> dispatchersList;
    private ArrayList<Machine> machinesList;



    public FactorySimulatorGUI(){
        dispatchersList = new ArrayList<>();
        machinesList = new ArrayList<>();

        super.setPreferredSize(new Dimension(1200, 700));
        setLayout(new BorderLayout());

        header = new JLabel(">>> Number of Dispatchers = "+dispatchersList.size()+
                ", Number of Machines = "+machinesList.size());

        add(header, BorderLayout.NORTH);

        machineDispatcherPanel = new MachineDispatcherPanel();
        add(machineDispatcherPanel, BorderLayout.CENTER);

        togglePanel = new TogglePanel();
        add(togglePanel, BorderLayout.SOUTH);

        timer = new Timer(10, this);
        timer.start();

        for (int i = 0 ; i < conveyorBelts.length; i++) {
            conveyorBelts[i] = new ConveyorBelt(8);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == timer){
            header.setText(">>> Number of Dispatchers = "+dispatchersList.size()+
                    ", Number of Machines = "+machinesList.size());
            machineDispatcherPanel.repaint();
        }
    }

    private class MachineDispatcherPanel extends JPanel{

        public MachineDispatcherPanel(){
            super();
            setBackground(Color.white);
        }

        public void paintComponent(Graphics g){
            super.paintComponent(g);

            for (int i = 0 ; i < conveyorBelts.length; i++) {
                conveyorBelts[i].drawBelt(g, 180, 40+(i*110), 100, 100);
            }
        }
    }

    private class TogglePanel extends JPanel implements ActionListener, ChangeListener {

        JButton addDispatcher, removeDispatcher, addMachine, removeMachine;
        JSlider maxConsumptionSlider, maxProductionSlider;

        public TogglePanel(){
            setLayout(new GridLayout(1, 6));
            super.setPreferredSize(new Dimension(700, 80));

            addDispatcher = new JButton("Add Dispatcher");
            addDispatcher.addActionListener(this);
            add(addDispatcher);

            removeDispatcher = new JButton("Remove Dispatcher");
            removeDispatcher.addActionListener(this);
            add(removeDispatcher);

            maxConsumptionSlider = new JSlider(Machine.MIN_CONSUMPTION_TIME, Machine.MAX_PRODUCTION_TIME,
                    Machine.MIN_CONSUMPTION_TIME);

            maxConsumptionSlider.setBorder(new TitledBorder("Max Consumption Time"));
            maxConsumptionSlider.addChangeListener(this);
            add(maxConsumptionSlider);

            addMachine = new JButton("Add Machine");
            addMachine.addActionListener(this);
            add(addMachine);

            removeMachine = new JButton("Remove Machine");
            removeMachine.addActionListener(this);
            add(removeMachine);

            maxProductionSlider = new JSlider(  Machine.MIN_PRODUCTION_TIME, Machine.MAX_PRODUCTION_TIME,
                    Machine.MIN_PRODUCTION_TIME);
            maxProductionSlider.addChangeListener(this);

            maxProductionSlider.setBorder(new TitledBorder("Max Production Time"));
            add(maxProductionSlider);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == addMachine){
                Machine machine = new Machine(conveyorBelts);
                machinesList.add(machine);
                Thread thread = new Thread(machine);
                thread.start();
            }
            if (e.getSource() == addDispatcher){
                System.out.println("Clicked");
                Dispatcher dispatcher = new Dispatcher(conveyorBelts);
                dispatchersList.add(dispatcher);
                Thread thread = new Thread(dispatcher);
                thread.start();
            }
            if (e.getSource() == removeDispatcher){
                if (!dispatchersList.isEmpty()) {
                    dispatchersList.get(dispatchersList.size() - 1).stop();
                    dispatchersList.remove(dispatchersList.size()-1);
                }
            }
            if (e.getSource() == removeMachine){
                if (!machinesList.isEmpty()){
                    machinesList.get(machinesList.size()-1).stop();
                    machinesList.remove(machinesList.size()-1);
                }
            }
        }

        @Override
        public void stateChanged(ChangeEvent e) {
            if (e.getSource() == maxProductionSlider) {
                System.out.println("Production Slider Changed!");
                Machine.MAX_PRODUCTION_TIME = maxProductionSlider.getValue();
                int value = maxProductionSlider.getValue();
                System.out.println(value);
            }
            else if (e.getSource() == maxConsumptionSlider){
                System.out.println("Consumption Slider Changed!");
                Machine.MAX_CONSUMPTION_TIME = maxConsumptionSlider.getValue();
                int value = maxConsumptionSlider.getValue();
                System.out.println(value);
            }
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.add(new FactorySimulatorGUI());
        frame.pack();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
