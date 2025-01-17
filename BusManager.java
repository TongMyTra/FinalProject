package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import Database.Database;

class BusManager extends JFrame{
	JTabbedPane menu;
	DefaultTableModel ticketTableModel, customerTableModel, busTableModel; 
	JTable ticketTable, customerTable, busTable;
	
	public BusManager() {
		setTitle("Management");
	   	setSize(1100,640);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		
		URL frameicon = SignIn.class.getResource("busicon.png");
		Image img = Toolkit.getDefaultToolkit().createImage(frameicon);
		setIconImage(img);
		
		
		JPanel titlePanel = new JPanel(new BorderLayout());
		JLabel titleLabel = new JLabel("TICKET MANAGEMENT", JLabel.CENTER);
		titleLabel.setFont(new Font("Serif", Font.BOLD, 30));
		titleLabel.setForeground(Color.DARK_GRAY);
		ImageIcon busMainIcon = new ImageIcon(getClass().getResource("busmain.png"));
        JLabel busMainLabel = new JLabel(busMainIcon);
		
		
		ImageIcon logout_Icon = new ImageIcon(getClass().getResource("logout-icon.png"));
		JLabel logoutLabel = new JLabel(logout_Icon);
		
		
		titlePanel.add(logoutLabel, BorderLayout.EAST);
	    titlePanel.add(titleLabel, BorderLayout.CENTER);
		titlePanel.add(busMainLabel, BorderLayout.WEST);
		
		logoutLabel.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				int response = JOptionPane.showOptionDialog(BusManager.this,"Are you sure?",null,JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,
                        new Object[]{"Yes", "No"},"Yes");
                        

                if (response == JOptionPane.YES_OPTION) {
                   dispose();
                   new SignIn();
                    
                }
				
			}
		});
			
			
		
		customerTableModel = new DefaultTableModel(new String[]{"Customer ID", "Name", "Phone", "Gender", "Membership", "Adress"}, 0);
		customerTable = new JTable(customerTableModel);
		JScrollPane customerScrollPane = new JScrollPane(customerTable);
		customerScrollPane.setSize(300, 200);
		
        
        JPanel customerInput = new JPanel(new GridLayout(11, 2, 5, 10));
        customerInput.add(new JLabel("Customer ID:"));
        JTextField customerId = new JTextField();
        customerInput.add(customerId);
        customerInput.add(new JLabel("Customer Name:"));
        JTextField customerName = new JTextField();
        customerInput.add(customerName);
        customerInput.add(new JLabel("Customer Phone:"));
        JTextField customerPhone = new JTextField();
        customerInput.add(customerPhone);
        customerInput.add(new JLabel("Gender:"));
        JRadioButton maleRadioButton = new JRadioButton("Male");
        JRadioButton femaleRadioButton = new JRadioButton("Female");
        ButtonGroup bg = new ButtonGroup();
        bg.add(maleRadioButton);
        bg.add(femaleRadioButton);
        JPanel genderPanel = new JPanel();
        genderPanel.add(maleRadioButton);
        genderPanel.add(femaleRadioButton);
        customerInput.add(genderPanel);
        customerInput.add(new JLabel("Membership:"));
        String[] member = {"Gold", "Sliver","Copper"}; 
        JComboBox<String> membership = new JComboBox<>(member);
        customerInput.add(membership);
        customerInput.add(new JLabel("Adress:"));
        JTextField customerAdress = new JTextField();
        customerInput.add(customerAdress);
        
        
        
        ImageIcon addCustomerIcon = new ImageIcon(getClass().getResource("adduser.png"));
        JButton addCustomerButton = new JButton("Add",addCustomerIcon);
        
        // Add Customer
        addCustomerButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				addCustomerFunction();
			}

			private void addCustomerFunction() {
				
					String name = customerName.getText();
					String phone = customerPhone.getText();
					String gender;
					if(maleRadioButton.isSelected()) {
						gender = "Male";
					}else {
						gender = "Female";
					}
					String member = (String) membership.getSelectedItem();
					String adress = customerAdress.getText();
					
					if(!name.isEmpty() && !phone.isEmpty() && !adress.isEmpty()) {
						Connection connect = Database.getConnection();
						try {
							PreparedStatement statement = connect.prepareStatement("INSERT INTO Customer ([Customer Name], [Customer Phone], Gender, Membership, Adress) VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
							
							statement.setString(1, name);
							statement.setString(2, phone);
							statement.setString(3, gender);
							statement.setString(4, member);
							statement.setString(5, adress);
							int rowsInserted = statement.executeUpdate();
				            if (rowsInserted > 0) {
				            	try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
	                                if (generatedKeys.next()) {
	                                    int generatedCustomerId = generatedKeys.getInt(1); 
	                                    JOptionPane.showMessageDialog(BusManager.this, "Successfully added customer with ID " + generatedCustomerId, null, JOptionPane.WARNING_MESSAGE);
				                
	                                    customerTableModel.addRow(new Object[] {generatedCustomerId, name, phone, gender, member, adress });

	                                    customerId.setText("");
	                                    customerName.setText("");
	                                    customerPhone.setText("");
	                                    maleRadioButton.setSelected(false);
	                                    femaleRadioButton.setSelected(false);
	                                    membership.setSelectedItem(0);
	                                    customerAdress.setText("");

	                                }
				            	}
				            }
						} catch (SQLException e) {
							
							e.printStackTrace();
						}
						
					}else {
						JOptionPane.showMessageDialog(BusManager.this,"Please fill in all details.", "Warning", JOptionPane.WARNING_MESSAGE );
					}
				
				
			}
		});
        
        
        ImageIcon deleteCustomerIcon = new ImageIcon(getClass().getResource("deleteuser.png"));
        JButton deleteCustomerButton = new JButton("Delete", deleteCustomerIcon);
        deleteCustomerButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				deleteCustomerFunction();
			}
			private void deleteCustomerFunction() {
				int selectedRow = customerTable.getSelectedRow();
				if (selectedRow != -1) {
		        	int selection = JOptionPane.showOptionDialog(BusManager.this,"Are you sure about deleting the customer?",null,JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,
	                        new Object[]{"Yes", "No"},"Yes");
	                        

	                if (selection == JOptionPane.YES_OPTION) {
	                	Object customerIdObject = customerTable.getValueAt(selectedRow, 0);
	                	String customerIdString = String.valueOf(customerIdObject);

	                    if (deleteCustomerFromDatabase(customerIdString)) {
	                        
	                        customerTableModel.removeRow(selectedRow);
	                        customerId.setText("");
			                customerName.setText("");
			                customerPhone.setText("");
			                customerAdress.setText("");
	                        JOptionPane.showMessageDialog(BusManager.this, "Successfully deleted customer.",
	                                null, JOptionPane.INFORMATION_MESSAGE);
	                    } else {
	                        JOptionPane.showMessageDialog(BusManager.this, "Failed to delete customer in database.",
	                                null, JOptionPane.ERROR_MESSAGE);
	                    }
	                } 
				}
			}
			
			public boolean deleteCustomerFromDatabase (String id) {
				Connection connect = Database.getConnection();
				try {
					PreparedStatement statement = connect.prepareStatement("DELETE FROM Customer WHERE ID = ?");
					statement.setString(1, id);
					int rowsAffected = statement.executeUpdate();
		            return rowsAffected > 0;
				} catch (SQLException e) {
					e.printStackTrace();
					return false;
				}
			}
		});
        
        
        ImageIcon updateCustomerIcon = new ImageIcon(getClass().getResource("Editicon.png"));
        JButton updateCustomerButton = new JButton("Update", updateCustomerIcon);
        updateCustomerButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = customerTable.getSelectedRow();
		        if (selectedRow != -1) {
		            String id = customerId.getText();
		            String cusName = customerName.getText();
		            String cusPhone = customerPhone.getText();
		            String gender;
		            if(maleRadioButton.isSelected()) {
						gender = "Male";
					}else {
						gender = "Female";
					}
		            String member = (String) membership.getSelectedItem();
		            String adress = customerAdress.getText();
		            

		            if ( !cusName.isEmpty() && !cusPhone.isEmpty() &&!adress.isEmpty()) {
		            	
		            	if (updateCustomerFromDatabase(cusName, cusPhone, gender, member, adress, id)) {
			                customerTableModel.setValueAt(cusName, selectedRow, 1);
			                customerTableModel.setValueAt(cusPhone, selectedRow, 2);
			                customerTableModel.setValueAt(gender, selectedRow, 3);
			                customerTableModel.setValueAt(member, selectedRow, 4);
			                customerTableModel.setValueAt(adress, selectedRow, 5);
			                
			                customerId.setText("");
			                customerName.setText("");
			                customerPhone.setText("");
			                customerAdress.setText("");
			                
		                    JOptionPane.showMessageDialog(BusManager.this, "Customer updated successfully", "Message", JOptionPane.INFORMATION_MESSAGE);
		                } else {
		                    JOptionPane.showMessageDialog(BusManager.this, "Failed to update customer", "Message", JOptionPane.ERROR_MESSAGE);
		                }
		            	
		                
		            } else {
		                JOptionPane.showMessageDialog(BusManager.this, "Please fill in all details", "Message", JOptionPane.ERROR_MESSAGE);
		            }
		        } else {
		            JOptionPane.showMessageDialog(BusManager.this, "Please select a customer to update", "Message", JOptionPane.ERROR_MESSAGE);
		        }
				
			}
			
			public boolean updateCustomerFromDatabase (String name, String phone, String gender, String membership, String adress, String id) {
				Connection connect = Database.getConnection();
				try {
					PreparedStatement statement = connect.prepareStatement("UPDATE Customer SET [Customer Name] = ?, [Customer Phone] = ?, Gender = ?, Membership = ?, Adress = ? WHERE ID = ?");
				    
					statement.setString(1, name);
					statement.setString(2, phone);
					statement.setString(3, gender);
					statement.setString(4, membership);
					statement.setString(5, adress);
					statement.setString(6, id);
					int rowsAffected = statement.executeUpdate();
		            return rowsAffected > 0;
				} catch (SQLException e) {
					e.printStackTrace();
					return false;
				}
			}
		});
        
        customerTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int selectRow = customerTable.getSelectedRow();
                if (selectRow != -1) {
                    
                    customerId.setText(customerTableModel.getValueAt(selectRow, 0).toString());
                    customerName.setText(customerTableModel.getValueAt(selectRow, 1).toString());
                    customerPhone.setText(customerTableModel.getValueAt(selectRow, 2).toString());
                    String gender = customerTableModel.getValueAt(selectRow, 3).toString();
                    if (gender.equals("Male")) {
                        maleRadioButton.setSelected(true);
                    } else if (gender.equals("Female")) {
                        femaleRadioButton.setSelected(true);
                    }
                    membership.setSelectedItem(customerTableModel.getValueAt(selectRow, 4).toString());
                    customerAdress.setText(customerTableModel.getValueAt(selectRow, 5).toString());
                }
            }
        });

        
        JPanel customerButton = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        customerButton.add(addCustomerButton);
        customerButton.add(deleteCustomerButton);
        customerButton.add(updateCustomerButton);
        
        JPanel customerPanel = new JPanel(new BorderLayout());
        customerPanel.add(customerInput, BorderLayout.EAST);
        customerPanel.add(customerButton, BorderLayout.SOUTH);
        customerPanel.add(customerScrollPane, BorderLayout.CENTER);
        queryCustomerTable();
        
        ticketTableModel = new DefaultTableModel(new String[]{"Ticket ID", "Bus Name","Cust Name","Seating","Price", "Date", "To", "From"}, 0);
        ticketTable = new JTable(ticketTableModel);
        JScrollPane ticketScrollPane = new JScrollPane(ticketTable);
        
        JPanel ticketInputPanel = new JPanel(new GridLayout(12, 2, 5, 10));
        ticketInputPanel.add(new JLabel("Ticket ID:"));
        JTextField ticketIdField = new JTextField();
        ticketInputPanel.add(ticketIdField);
        ticketInputPanel.add(new JLabel("Bus Name:"));
        JTextField busField = new JTextField();
        ticketInputPanel.add(busField);
        ticketInputPanel.add(new JLabel("Customer Name:"));
        JTextField ticketCustomerNameField = new JTextField();
        ticketInputPanel.add(ticketCustomerNameField);
        ticketInputPanel.add(new JLabel("Seating:"));
        String[] seat = {"A1","A2","A3", "A4", "A5","B1","B2", "B3", "B4", "B5"};
        JComboBox<String> seatingBox = new JComboBox<>(seat);
        ticketInputPanel.add(seatingBox);
        ticketInputPanel.add(new JLabel("Price:"));
        Integer[] price = {100000, 150000, 200000, 250000, 300000};
        JComboBox<Integer> priceBox = new JComboBox<>(price);
        ticketInputPanel.add(priceBox);
        ticketInputPanel.add(new JLabel("Date:"));
        JTextField dateField = new JTextField();
        ticketInputPanel.add(dateField);
        ticketInputPanel.add(new JLabel("To: "));
        String[] placeTo = {"TP.Hồ Chí Minh","Hà Nội", "Đà Nẵng"};
        JComboBox<String> toTicketBox = new JComboBox<String>(placeTo);
        ticketInputPanel.add(toTicketBox);
        ticketInputPanel.add(new JLabel("From:")); 
        String[] placeFrom = {"TP.Hồ Chí Minh","Hà Nội", "Đà Nẵng"};
        JComboBox<String> fromTicketBox = new JComboBox<String>(placeFrom);
        ticketInputPanel.add(fromTicketBox);
        JTextField searchTicketField = new JTextField();
        ticketInputPanel.add(searchTicketField);
        ImageIcon searchIcon = new ImageIcon(getClass().getResource("searchIcon.png"));
        JLabel searchTicketLabel = new JLabel(searchIcon, JLabel.LEFT);
        ticketInputPanel.add(searchTicketLabel);
        searchTicketLabel.addMouseListener(new MouseListener() {
			
			
			@Override
			
			public void mouseClicked(MouseEvent e) {
			    String inputSearch = searchTicketField.getText();
			    if (!inputSearch.isEmpty()) {
			        Connection connect = Database.getConnection();
			        try {
			            PreparedStatement statement = connect.prepareStatement(
			                "SELECT [Ticket ID], [Bus Name], [Customer Name], Seating, Price, [DayTicket], [To], [From] FROM Ticket WHERE [Ticket ID] LIKE ? OR [Customer Name] ?"
			            );
			            statement.setString(1, "%" + inputSearch + "%");
			            statement.setString(2,"%" + inputSearch +"%");
			            ResultSet resultSet = statement.executeQuery();

			            // Kiểm tra nếu ResultSet có dữ liệu
			            if (resultSet.next()) {
			                // Lấy thông tin từ ResultSet
			                String busName = resultSet.getString("Bus Name");
			                String custName = resultSet.getString("Customer Name");
			                String seating = resultSet.getString("Seating");
			                int price = resultSet.getInt("Price");
			                String date = resultSet.getString("DayTicket");
			                String to = resultSet.getString("To");
			                String from = resultSet.getString("From");

			                // Hiển thị dữ liệu trong các trường nhập liệu
			                ticketIdField.setText(inputSearch);
			                busField.setText(busName);
			                ticketCustomerNameField.setText(custName);
			                seatingBox.setSelectedItem(seating);
			                priceBox.setSelectedItem(price);
			                dateField.setText(date);
			                toTicketBox.setSelectedItem(to);
			                fromTicketBox.setSelectedItem(from);
			            } else {
			                JOptionPane.showMessageDialog(null,"No ticket found for the given ID","No Results",JOptionPane.WARNING_MESSAGE);
			            }
			        } catch (SQLException e2) {
			            e2.printStackTrace();
			            JOptionPane.showMessageDialog(null,"Error searching ticket data by ID","Error",JOptionPane.ERROR_MESSAGE);
			        
			        }
			    } else {
			        JOptionPane.showMessageDialog(BusManager.this,"Please enter the keyword search","Warning",JOptionPane.WARNING_MESSAGE);
			    }
			}


			@Override
			public void mousePressed(MouseEvent e) {
				
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				
			}
		});
        // Add Ticket 
        ImageIcon addTicketIcon = new ImageIcon(getClass().getResource("Add-ticket-icon (1).png"));
        JButton addTicketButton = new JButton("Add", addTicketIcon);

        addTicketButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	
                // Lấy thông tin từ giao diện
                String busName = busField.getText();
                String cusName = ticketCustomerNameField.getText();
                String seating = (String) seatingBox.getSelectedItem();
                int price = (int) priceBox.getSelectedItem();
                String date = dateField.getText();
                String fromPlace = (String) fromTicketBox.getSelectedItem();
                String toPlace = (String) toTicketBox.getSelectedItem();

                // Kiểm tra các trường bắt buộc
                if (!busName.isEmpty() && !cusName.isEmpty() && seating != null && !date.isEmpty()) {
                    Connection connect = Database.getConnection();
                    try {
                        // Kiểm tra trùng lặp vé
                        String checkTicketQuery = "SELECT COUNT(*) FROM Ticket WHERE [Bus Name] = ? AND [Customer Name] = ? AND Seating = ? AND DayTicket = ?";
                        PreparedStatement checkTicketStatement = connect.prepareStatement(checkTicketQuery);
                        checkTicketStatement.setString(1, busName);
                        checkTicketStatement.setString(2, cusName);
                        checkTicketStatement.setString(3, seating);
                        checkTicketStatement.setString(4, date);
                        ResultSet ticketResultSet = checkTicketStatement.executeQuery();

                        if (ticketResultSet.next() && ticketResultSet.getInt(1) > 0) {
                            // Nếu vé đã tồn tại
                            JOptionPane.showMessageDialog(BusManager.this, 
                                "This ticket already exists. Please check the details.", 
                                "Duplicate Ticket", 
                                JOptionPane.ERROR_MESSAGE);
                        } else {
                            // Kiểm tra trùng lặp chỗ ngồi
                            String checkSeatingQuery = "SELECT COUNT(*) FROM Ticket WHERE [Bus Name] = ? AND Seating = ? AND DayTicket = ?";
                            PreparedStatement checkSeatingStatement = connect.prepareStatement(checkSeatingQuery);
                            checkSeatingStatement.setString(1, busName);
                            checkSeatingStatement.setString(2, seating);
                            checkSeatingStatement.setString(3, date);
                            ResultSet seatingResultSet = checkSeatingStatement.executeQuery();

                            if (seatingResultSet.next() && seatingResultSet.getInt(1) > 0) {
                                // Nếu chỗ ngồi đã được đặt
                                JOptionPane.showMessageDialog(BusManager.this, "The selected seating is already taken for this bus and date.", "Seating Conflict", JOptionPane.ERROR_MESSAGE);
                            } else {
                                // Thêm vé mới nếu không có trùng lặp
                                String insertQuery = "INSERT INTO Ticket ([Bus Name], [Customer Name], Seating, Price, DayTicket, [To], [From]) VALUES (?, ?, ?, ?, ?, ?, ?)";
                                PreparedStatement insertStatement = connect.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
                                insertStatement.setString(1, busName);
                                insertStatement.setString(2, cusName);
                                insertStatement.setString(3, seating);
                                insertStatement.setInt(4, price);
                                insertStatement.setString(5, date);
                                insertStatement.setString(6, toPlace);
                                insertStatement.setString(7, fromPlace);

                                int rowsInserted = insertStatement.executeUpdate();

                                if (rowsInserted > 0) {
                                    
                                    try (ResultSet generatedKeys = insertStatement.getGeneratedKeys()) {
                                        if (generatedKeys.next()) {
                                            int generatedTicketId = generatedKeys.getInt(1);
                                            JOptionPane.showMessageDialog(BusManager.this, 
                                                "Successfully added ticket with ID " + generatedTicketId, "Success",JOptionPane.INFORMATION_MESSAGE);

                                            // Thêm vé vào bảng
                                            ticketTableModel.addRow(new Object[] {generatedTicketId, busName, cusName, seating, price, date, toPlace, fromPlace});

                                            // Xóa dữ liệu trong các trường
                                            ticketIdField.setText("");
                                            busField.setText("");
                                            ticketCustomerNameField.setText("");
                                            dateField.setText("");
                                        }
                                    }
                                }
                            }
                        }
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                        
                    }
                } else {
                    
                    JOptionPane.showMessageDialog(BusManager.this,"Please fill in all required details.","Warning", JOptionPane.WARNING_MESSAGE);
                }
            }
        });


        
        
        
        // Delete Ticket
        ImageIcon deleteTicketIcon = new ImageIcon(getClass().getResource("Remove-ticket-icon.png"));
        JButton deleteTicketButton = new JButton("Delete", deleteTicketIcon);
        deleteTicketButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = ticketTable.getSelectedRow();
				if (selectedRow != -1) {
		        	int selection = JOptionPane.showOptionDialog(BusManager.this,"Are you sure about deleting the ticket?",null,JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,
	                        new Object[]{"Yes", "No"},"Yes");
	                        

		        	if (selection == JOptionPane.YES_OPTION) {
	                	Object ticketIdObject = ticketTable.getValueAt(selectedRow, 0);
	                	String ticketIdString = String.valueOf(ticketIdObject);

	                    if (deleteTicketFromDatabase(ticketIdString)) {
	                        
	                        ticketTableModel.removeRow(selectedRow);
	                        ticketIdField.setText("");
			                busField.setText("");
			                ticketCustomerNameField.setText("");
			                dateField.setText("");
			                
	                        JOptionPane.showMessageDialog(BusManager.this, "Successfully deleted ticket.",
	                                null, JOptionPane.INFORMATION_MESSAGE);
	                    } else {
	                        JOptionPane.showMessageDialog(BusManager.this, "Failed to delete ticket.",
	                                null, JOptionPane.ERROR_MESSAGE);
	                    }
	                } 
				}
				
			}
			
			public boolean deleteTicketFromDatabase (String id) {
				Connection connect = Database.getConnection();
				try {
					PreparedStatement statement = connect.prepareStatement("DELETE FROM Ticket WHERE [Ticket ID] = ?");
					statement.setString(1, id);
					int rowsAffected = statement.executeUpdate();
		            return rowsAffected > 0;
				} catch (SQLException e2) {
					e2.printStackTrace();
					return false;
				}
			}
		});
        
        // Update Ticket
        ImageIcon updateTicketIcon = new ImageIcon(getClass().getResource("Editicon.png"));
        JButton updateTicketButton = new JButton("Update", updateTicketIcon);
        updateTicketButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = ticketTable.getSelectedRow();
                if (selectedRow != -1) {
                    String busName = busField.getText();
                    String cusName = ticketCustomerNameField.getText();
                    String seating = (String) seatingBox.getSelectedItem();
                    int price;
                    try {
                        price = Integer.parseInt(priceBox.getSelectedItem().toString());
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(BusManager.this, "Invalid price format", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    String date = dateField.getText();
                    String toPlace = (String) toTicketBox.getSelectedItem();
                    String fromPlace = (String) fromTicketBox.getSelectedItem();
                    String ticketId = ticketIdField.getText(); 

                    if (!busName.isEmpty() && !cusName.isEmpty() && !ticketId.isEmpty()) {
                        if (updateTicketFromDatabase(ticketId, busName, cusName, seating, price, date, toPlace, fromPlace)) {
                            ticketTableModel.setValueAt(busName, selectedRow, 1);
                            ticketTableModel.setValueAt(cusName, selectedRow, 2);
                            ticketTableModel.setValueAt(seating, selectedRow, 3);
                            ticketTableModel.setValueAt(price, selectedRow, 4);
                            ticketTableModel.setValueAt(date, selectedRow, 5);
                            ticketTableModel.setValueAt(toPlace, selectedRow, 6);
                            ticketTableModel.setValueAt(fromPlace, selectedRow, 7);

                            ticketIdField.setText("");
                            busField.setText("");
                            ticketCustomerNameField.setText("");
                            customerAdress.setText("");
                            dateField.setText("");

                            JOptionPane.showMessageDialog(BusManager.this, "Ticket updated successfully", "Message", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(BusManager.this, "Ticket update failed", "Message", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(BusManager.this, "Please fill in all details", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(BusManager.this, "Please select a ticket to update", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

            public boolean updateTicketFromDatabase(String ticketId, String busName, String cusName, String seating, int price, String date, String to, String from) {
                Connection connect = Database.getConnection();
                try {
                    PreparedStatement statement = connect.prepareStatement(
                        "UPDATE Ticket SET [Bus Name] = ?, [Customer Name] = ?, Seating = ?, Price = ?, DayTicket = ?, [To] = ?, [From] = ? WHERE [Ticket ID] = ?"
                    );
                    statement.setString(1, busName);
                    statement.setString(2, cusName);
                    statement.setString(3, seating);
                    statement.setInt(4, price);
                    statement.setString(5, date);
                    statement.setString(6, to);
                    statement.setString(7, from);
                    statement.setString(8, ticketId); // Điều kiện WHERE
                    int rowsAffected = statement.executeUpdate();
                    return rowsAffected > 0;
                } catch (SQLException e) {
                    e.printStackTrace();
                    return false;
                }
            }
        });

        ticketTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				int selectRow = ticketTable.getSelectedRow();
				if (selectRow != -1) {
					ticketIdField.setText(ticketTableModel.getValueAt(selectRow, 0).toString());
					busField.setText(ticketTableModel.getValueAt(selectRow, 1).toString());
					ticketCustomerNameField.setText(ticketTableModel.getValueAt(selectRow, 2).toString());
					seatingBox.setSelectedItem(ticketTableModel.getValueAt(selectRow, 3).toString());
					priceBox.setSelectedItem(ticketTableModel.getValueAt(selectRow, 4));
					dateField.setText(ticketTableModel.getValueAt(selectRow, 5).toString());
					toTicketBox.setSelectedItem(ticketTableModel.getValueAt(selectRow, 6).toString());
					fromTicketBox.setSelectedItem(ticketTableModel.getValueAt(selectRow, 7).toString());
					
				}
				
			}
		});
        
        JPanel ticketButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        ticketButtonPanel.add(addTicketButton);
        ticketButtonPanel.add(deleteTicketButton);
        ticketButtonPanel.add(updateTicketButton);
        
        JPanel ticketPanel = new JPanel(new BorderLayout());
        ticketPanel.add(ticketInputPanel, BorderLayout.EAST);
        ticketPanel.add(ticketButtonPanel, BorderLayout.SOUTH);
        ticketPanel.add(ticketScrollPane, BorderLayout.CENTER);
        queryTicketTable();
        
        busTableModel = new DefaultTableModel(new String[] {"Bus ID", "Bus Name", "License Plate","Date of registration", "Status"}, 0);
        busTable = new JTable(busTableModel);
        JScrollPane busScrollPane = new JScrollPane(busTable);
        queryBusTable();
        
        JPanel busInputPanel = new JPanel(new GridLayout(15, 2, 10, 2));
        busInputPanel.add(new JLabel("Bus ID:"));
        JTextField busIdField = new JTextField();
        busInputPanel.add(busIdField);
        busInputPanel.add(new JLabel("Bus Name:"));
        JTextField busNameField = new JTextField();
        busInputPanel.add(busNameField);
        busInputPanel.add(new JLabel("License Plate:"));
        JTextField licensePlateField = new JTextField();
        busInputPanel.add(licensePlateField);
        busInputPanel.add(new JLabel("Date of registration:                   "));
        JTextField daysBus = new JTextField();
        busInputPanel.add(daysBus);
        busInputPanel.add(new JLabel("Status:"));
        String[] statusBus = {"Normal","New", "Old"};
        JComboBox<String> statusBox = new JComboBox<>(statusBus);
        busInputPanel.add(statusBox);
        
        
        ImageIcon addBusIcon = new ImageIcon(getClass().getResource("addicon.png"));
        JButton addBusButton = new JButton("Add", addBusIcon);
        addBusButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			
				String name = busNameField.getText();
				String bsx = licensePlateField.getText();
				String date = daysBus.getText();
				String status = (String)statusBox.getSelectedItem();
				if (!name.isEmpty() && !bsx.isEmpty()) {
					Connection connect = Database.getConnection();
					try {
						PreparedStatement statement = connect.prepareStatement("INSERT INTO Bus ([Bus Name], [License Plante],[Date of registration], StatusBus) VALUES (?, ?, ?, ?) ", Statement.RETURN_GENERATED_KEYS);
						
						statement.setString(1, name);
						statement.setString(2, bsx);
						statement.setString(3, date);
						statement.setString(4, status);
						int rowsInserted = statement.executeUpdate();
			            if (rowsInserted > 0) {
			            	try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                                if (generatedKeys.next()) {
                                    int generatedBusId = generatedKeys.getInt(1); 
                                    JOptionPane.showMessageDialog(BusManager.this, "Successfully added bus with ID " + generatedBusId, null, JOptionPane.WARNING_MESSAGE);
			                
                                    busTableModel.addRow(new Object[] {generatedBusId , name, bsx, date, status });
                                    busIdField.setText("");
                                    busNameField.setText("");
                                    licensePlateField.setText("");
                                    daysBus.setText("");
                                }
			            	}
			                
			            }
					} catch (SQLException e1) {
						
						e1.printStackTrace();
					}
				}else {
					JOptionPane.showMessageDialog(BusManager.this, "Please fill in all details", "Error", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
        
        
        ImageIcon deleteBusIcon = new ImageIcon(getClass().getResource("deleteicon.png"));
        JButton deleteBusButton = new JButton("Delete", deleteBusIcon);
        deleteBusButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = busTable.getSelectedRow();
		        if (selectedRow != -1) {
		        	int response = JOptionPane.showOptionDialog(BusManager.this,"Are you sure about deleting the bus?",null,JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,
	                        new Object[]{"Yes", "No"},"Yes");
	                        

	                if (response == JOptionPane.YES_OPTION) {
	                	Object busIdObject = busTable.getValueAt(selectedRow, 0);
	                	String busIdString = String.valueOf(busIdObject);

	                    if (deleteBusFromDatabase(busIdString)) {
	                        
	                        busTableModel.removeRow(selectedRow);
	                        busIdField.setText("");
			                busNameField.setText("");
			                licensePlateField.setText("");
	                        JOptionPane.showMessageDialog(BusManager.this, "Successfully deleted bus.",
	                                null, JOptionPane.INFORMATION_MESSAGE);
	                    } else {
	                        JOptionPane.showMessageDialog(BusManager.this, "Failed to delete bus.",
	                                null, JOptionPane.ERROR_MESSAGE);
	                    }
	                }
		        }
				
			}
			
			public boolean deleteBusFromDatabase (String id) {
				Connection connect = Database.getConnection();
				try {
					PreparedStatement statement = connect.prepareStatement("DELETE FROM Bus WHERE ID = ?");
					statement.setString(1, id);
					int rowsAffected = statement.executeUpdate();
		            return rowsAffected > 0;
				} catch (SQLException e2) {
					e2.printStackTrace();
					return false;
				}
			}
		});
        
        
        JButton updateBusButton = new JButton("Update", updateCustomerIcon);
        updateBusButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = busTable.getSelectedRow();
		        if (selectedRow != -1) {
		            String id = busIdField.getText();
		            String busName = busNameField.getText();
		            String bsx = licensePlateField.getText();
		            String date = daysBus.getText();
		            String status = (String) statusBox.getSelectedItem(); 
		            
		            if (!busName.isEmpty() && !bsx.isEmpty()) {
		            	if (updateBusFromDatabase(busName, bsx, date, status, id)) {
		            		busTableModel.setValueAt(busName, selectedRow, 1);
		            		busTableModel.setValueAt(bsx, selectedRow, 2);
		            		busTableModel.setValueAt(date, selectedRow, 3);
		            		busTableModel.setValueAt(status, selectedRow, 4);
		            		
			                
		            		busIdField.setText("");
		            		busNameField.setText("");
		            		licensePlateField.setText("");
			                
			                
		                    JOptionPane.showMessageDialog(BusManager.this, "Bus updated successfully", "Message", JOptionPane.INFORMATION_MESSAGE);
		                } else {
		                    JOptionPane.showMessageDialog(BusManager.this, "Failed to update bus in database", "Message", JOptionPane.ERROR_MESSAGE);
		                }
		                
		            } else {
		                JOptionPane.showMessageDialog(BusManager.this, "Please fill in all details", "Error", JOptionPane.ERROR_MESSAGE);
		            }
		        } else {
		            JOptionPane.showMessageDialog(BusManager.this, "Please select a buss to update", "Error", JOptionPane.ERROR_MESSAGE);
		        }
				
			}
			
			public boolean updateBusFromDatabase (String busName, String licensePlante, String date, String status, String id) {
				Connection connect = Database.getConnection();
				try {
					PreparedStatement statement = connect.prepareStatement("UPDATE Bus SET [Bus Name] = ?, [License Plante] = ?, [Date of registration] = ?, StatusBus = ? WHERE ID = ? ");
					
					statement.setString(1, busName);
					statement.setString(2, licensePlante);
					statement.setString(3, date);
					statement.setString(4, status);
					statement.setString(5, id);
					int rowsAffected = statement.executeUpdate();
		            return rowsAffected > 0;
				} catch (SQLException e) {
					e.printStackTrace();
					return false;
				}
			}
		});
        
        busTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				int selectRow = busTable.getSelectedRow();
				if(selectRow != -1) {
					busIdField.setText(busTableModel.getValueAt(selectRow,0).toString());
					busNameField.setText(busTableModel.getValueAt(selectRow, 1).toString());
					licensePlateField.setText(busTableModel.getValueAt(selectRow, 2).toString());
					daysBus.setText(busTable.getValueAt(selectRow, 3).toString());
					statusBox.setSelectedItem(busTableModel.getValueAt(selectRow, 4));
				}
				
			}
		});
        
        JPanel busButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        busButtonPanel.add(addBusButton);
        busButtonPanel.add(deleteBusButton);
        busButtonPanel.add(updateBusButton);
        
        JPanel busPanel = new JPanel(new BorderLayout());
        busPanel.add(busInputPanel, BorderLayout.EAST);
        busPanel.add(busButtonPanel, BorderLayout.SOUTH);
        busPanel.add(busScrollPane, BorderLayout.CENTER);
        
        
        
        JTabbedPane tab = new JTabbedPane();
        tab.add("Ticket", ticketPanel);
        tab.add("Customer",customerPanel);
        tab.add("Bus",busPanel);
        
        
        JLabel adminLabel = new JLabel("By: TONGMYTRA - 24IT275", JLabel.CENTER);
        adminLabel.setFont(new Font("Serif", Font.BOLD,15));
        
        add(tab, BorderLayout.CENTER);
        add(titlePanel, BorderLayout.NORTH);
        add(adminLabel, BorderLayout.SOUTH);
		setVisible(true);
		
		
	}
	public void queryCustomerTable () {
		Connection connect = Database.getConnection();
		try {
			Statement statement =  connect.createStatement();
			ResultSet result = statement.executeQuery("SELECT * FROM Customer");
			
			while (result.next()) {
				 String id = result.getString("ID");
				 String custName = result.getString("Customer Name");
				 String custPhone = result.getString("Customer Phone");
				 String gender = result.getString("Gender");
				 String membership = result.getString("Membership");
				 String adress = result.getString("Adress");
				 customerTableModel.addRow(new Object [] {id, custName, custPhone, gender, membership, adress});
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, "Error", null, JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
		}
		
	}
	
	public void queryTicketTable() {
		Connection connect = Database.getConnection();
		try {
			Statement statement =  connect.createStatement();
			ResultSet result = statement.executeQuery("SELECT * FROM Ticket");
			
			while (result.next()) {
				 int id = result.getInt("Ticket ID");
				 String busName = result.getString("Bus Name");
				 String custName = result.getString("Customer Name");
				 String seating = result.getString("Seating");
				 int price = result.getInt("Price");
				 String date = result.getString("DayTicket");
				 String toPlace = result.getString("To");
				 String fromPlace = result.getString("From");
				 
				 ticketTableModel.addRow(new Object [] {id, busName, custName, seating, price, date, toPlace, fromPlace});
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, "Error", null, JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
		}
	}
	
	public void queryBusTable() {
		Connection connect = Database.getConnection();
		try {
			Statement statement =  connect.createStatement();
			ResultSet result = statement.executeQuery("SELECT * FROM Bus");
			
			while (result.next()) {
				 String id = result.getString("ID");
				 String busName = result.getString("Bus Name");
				 String licenseplante = result.getString("License Plante");
				 String date = result.getString("Date of registration");
				 String status = result.getString("StatusBus");
				 busTableModel.addRow(new Object [] {id, busName, licenseplante, date, status});
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, "Error", null, JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
		}
	}
}



