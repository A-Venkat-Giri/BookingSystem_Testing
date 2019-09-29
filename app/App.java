package com.bbs.app;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import com.bbs.beans.Admin;
import com.bbs.beans.Available;
import com.bbs.beans.Booking;
import com.bbs.beans.Bus;
import com.bbs.beans.Ticket;
import com.bbs.beans.User;
import com.bbs.exception.BookingFailedException;
import com.bbs.exception.CancelFailedException;
import com.bbs.exception.CustomException;
import com.bbs.exception.DeleteFailedException;
import com.bbs.exception.LoginException;
import com.bbs.exception.TicketRetrievalFailedException;
import com.bbs.exception.UpdateFailedException;
import com.bbs.repo.UserRepository;
import com.bbs.services.AdminServicImpl;
import com.bbs.services.ServiceAdmin;
import com.bbs.services.ServiceUser;
import com.bbs.services.UserServiceImpl;

public class App {

	static int userId;
	static int bookingId;
	static int adminId;
	static int busId;

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		String password;
		ServiceUser service = new UserServiceImpl();
		ServiceAdmin adminService = new AdminServicImpl();
		UserRepository repo = new UserRepository();

		Ticket ticket = new Ticket();

		Boolean state = true;
		while (state) {
			System.out.println("<------------------------------------------->");
			System.out.println("Welcome to Bus Booking System");
			System.out.println("<------------------------------------------->");
			System.out.println("1.Login");
			System.out.println("2.Create Account");
			System.out.println("9.Admin Login");
			System.out.println("10.Exit");
			System.out.println("<------------------------------------------->");
			System.out.print("Enter Choice :");
			int firstChoice = Integer.parseInt(service.checkUserIdAndBookinIdAndBusId((sc.next())));
			if (firstChoice == 1) {
				boolean check = true;
				/**It checks whether the UserId is unique or not**/
				System.out.println("Enter UserId");
				while (check) {
					String uid = service.checkUserIdAndBookinIdAndBusId((sc.next()));
					if (uid != null) {

						userId = Integer.parseInt(uid);
						check = false;
					} else {
						LoginException exception = new LoginException("LoginException:Give Integer UserId ");
						exception.getMessage();
					}
				}
				User user = service.searchUser(userId);
				Boolean login = false;
				if (user != null) {
					System.out.println("Enter password");
					password = sc.next();
					login = service.loginUser(userId, password);

				} else {
					LoginException exception = new LoginException("LoginException:UserId doesnot exists");
					exception.getMessage();
				}

				while (login) {
					System.out.println("Login : " + login);
					System.out.println("<------------------------------------------->");
					System.out.println("Welcome to Login Page");
					System.out.println("1.Update Info ");
					System.out.println("2.Delete Profile");
					System.out.println("3.User Details");
					System.out.println("4.Book Ticket");
					System.out.println("5.View Ticket");
					System.out.println("6.Cancel Ticket");
					System.out.println("7.Logout");
					System.out.println("<------------------------------------------->");
					System.out.print("ENTER CHOICE :");
					int choice = Integer.parseInt(service.checkUserIdAndBookinIdAndBusId((sc.next())));
					/**Used for updating the user information**/
					if (choice == 1) {
						System.out.println("For Authentication add Password");
						password = sc.next();
						System.out.println("Enter New Password ");
						String newPassword = sc.next();
						if (service.updateUser(userId, password, newPassword)) {
							System.out.println("Info Updated");
						} else {
							System.out.println("Please Try Again....");
						}
						/**Used for deleting the user account**/
					} else if (choice == 2) {

						System.out.println("To Confirm your request Type Password");
						password = sc.next();
						if (service.deleteUser(userId, password)) {
							System.out.println("Account Deleted");
							System.out.println("<------------------------------------------->");
							login = false;
						} else {
							System.out.println("Please Try Again....");
						}
						/**Used for entering the user details**/
					} else if (choice == 3) {
						user = service.searchUser(userId);
						System.out.println("UserId : " + user.getUserId());
						System.out.println("Username : " + user.getUserName());
						System.out.println("Email : " + user.getEmail());
						System.out.println("Contact : " + user.getContact());
						System.out.println("<------------------------------------------->");
						
						/**Used for booking the ticket for the user**/
					} else if (choice == 4) {
						try {
							ticket.setUserId(userId);
							System.out.println("Enter Source ");
							ticket.setSource(sc.next());
							System.out.println("Enter Destination");
							ticket.setDestination(sc.next());
							System.out.println("Enter Date iN YYYY-MM-DD FORMAT");
							ticket.setDate(service.checkDate(sc.next()));
							Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse(ticket.getDate());
							java.sql.Date date = new java.sql.Date(date2.getTime());

							List<Bus> buses = service.searchBus(ticket.getSource(), ticket.getDestination(), date);
							Bus bus = buses.get(0);
							if (bus != null) {
								System.out.println(buses);

								System.out.println("Enter Bus Id");
								check = true;
								while (check) {
									String uid = service.checkUserIdAndBookinIdAndBusId((sc.next()));
									if (uid != null) {

										ticket.setBusId(Integer.parseInt(uid));
										check = false;
									} else {
										CustomException exception = new CustomException(
												"CustomException:Enter Integer Id");
										exception.getMessage();
									}
								}
								bus = service.searchBus(ticket.getBusId());
								if (bus != null) {
									Integer availSeats = service.checkAvailability(ticket.getBusId(), date);

									System.out.println("Enter Number of tickets ");
									check = true;
									while (check) {
										String uid = service.checkUserIdAndBookinIdAndBusId((sc.next()));
										if (uid != null) {

											ticket.setNumberOfSeats((Integer.parseInt(uid)));
											check = false;
										} else {
											BookingFailedException exception = new BookingFailedException(
													"BookingFailedException:Provide Proper Integer");
										}
									}
									if (availSeats >= ticket.getNumberOfSeats()) {
										Booking booking = service.bookTicket(ticket);
										if (booking != null) {
											System.out.println(booking);
											System.out.println("Ticket Booked Succesfully");
										} else {
											BookingFailedException exception = new BookingFailedException(
													"BookingFailedException:InternalError");
											exception.getMessage();
										}
									} else {

										BookingFailedException exception = new BookingFailedException(
												"BookingFailedException:NoTicketsAvailable");
										exception.getMessage();
									}
								} else {
									CustomException exception = new CustomException(
											"CustomException:Enter Valid Bus Id");
									exception.getMessage();
								}
							} else {
								CustomException exception = new CustomException("CustomException:No Bus Available");
								exception.getMessage();
							}
						} catch (Exception e) {
							CustomException exception = new CustomException("CustomException:No Bus Available");
							exception.getMessage();

						}
						
					/**Used for viewing the ticket**/
					} else if (choice == 5) {
						List<Booking> bookings = service.getAllTickets(userId);
						for (Booking booking : bookings) {
							System.out.println(booking);
						}
						System.out.println("Enter Booking Id");
						check = true;
						while (check) {
							String uid = service.checkUserIdAndBookinIdAndBusId((sc.next()));
							if (uid != null) {

								bookingId = Integer.parseInt(uid);
								check = false;
							} else {
								CustomException exception = new CustomException(
										"CustomException:BookingId should be Integer");
								exception.getMessage();
							}
						}
						Booking booking = service.getTicket(bookingId, userId);
						if (booking != null) {
							System.out.println(booking);
						} else {
							TicketRetrievalFailedException exception = new TicketRetrievalFailedException(
									"TicketRetrievalFailedException:BookingId not found");
							exception.getMessage();
						}
						
					/**Used for cancelling the ticket**/
					} else if (choice == 6) {
						List<Booking> bookings = service.getAllTickets(userId);
						for (Booking booking : bookings) {
							System.out.println(booking);
						}
						check = true;
						System.out.println("Enter Booking ID");
						while (check) {
							String uid = service.checkUserIdAndBookinIdAndBusId((sc.next()));
							if (uid != null) {

								bookingId = Integer.parseInt(uid);
								check = false;
							} else {
								CustomException exception = new CustomException(
										"CustomException:BookingId should be Integer");
								exception.getMessage();
							}
						}
						Booking booking = service.getTicket(bookingId, userId);
						if (booking != null) {
							System.out.println(booking);
							Boolean del = service.cancelTicket(booking);
							if (del) {
								System.out.println("Successfully Cancelled");
							} else {
								CancelFailedException exception = new CancelFailedException(
										"CancelFailedException:Internal Error");
								exception.getMessage();
							}
						} else {
							CancelFailedException exception = new CancelFailedException(
									"CancelFailedException:Cant Find Booking Id");
							exception.getMessage();
						}
						
				/**Used for logging out from the login's page**/
					} else if (choice == 7) {
						login = false;

					}

					else {
						System.out.println("Command Entered is Invalid");
					}

				}
				
			/**Creating the new user account**/	
			} else if (firstChoice == 2) {
				User user4 = new User();
				System.out.println("Enter User Id");
				Boolean check = true;
				while (check) {
					String uid = service.checkUserIdAndBookinIdAndBusId((sc.next()));
					if (uid != null) {
						user4.setUserId(Integer.parseInt(uid));
						check = false;
					} else {
						CustomException exception = new CustomException("CustomException:UserId Should be Integer");
						exception.getMessage();
					}
				}
				User user = service.searchUser(user4.getUserId());
				if (user != null) {
					CustomException exception = new CustomException("CustomException:UserId Already Exist");
					exception.getMessage();
					break;
				} else {
					System.out.println("Enter Email");
					String email = service.checkEmail(sc.next());
					if (email != null) {
						user4.setEmail(email);
					} else {
						break;
					}
					user = service.searchUser(user4.getEmail());
					if (user != null) {
						CustomException exception = new CustomException("CustomException:Email Already Exist");
						exception.getMessage();
						break;
					} else {
						System.out.println("Enter UserName");
						Scanner scan = new Scanner(System.in);
						String userName = scan.nextLine();
						System.out.println("\n");
						user4.setUserName(userName);
						System.out.println("Enter Password");
						user4.setPassword(sc.next());
						System.out.println("Enter Contact number");
						check = true;
						while (check) {
							String uid = service.checkContact(sc.next());
							if (uid != null) {
								user4.setContact(Long.parseLong(uid));
								check = false;
							} else {
								CustomException exception = new CustomException(
										"CustomException:Provide Proper Contact");
								exception.getMessage();
							}
						}
						if (service.createUser(user4)) {
							System.out.println("User Added");
						} else {
							CustomException exception = new CustomException("CustomException:Profile Creation Failed");
							exception.getMessage();
						}
					}
				}
			
			/**Used for existing Admin's Login**/
			} else if (firstChoice == 9) {
				System.out.println("Enter Admin Id");
				Boolean check = true;
				while (check) {
					String uid = adminService.checkUserIdAndBookinIdAndBusId((sc.next()));
					if (uid != null) {
						adminId = Integer.parseInt(uid);
						check = false;
					} else {

						CustomException exception = new CustomException("LoginException:Provide Integer AdminId");
						exception.getMessage();
					}
				}
				Admin admin = adminService.searchAdmin(adminId);
				Boolean adminLogin = false;
				if (admin != null) {
					System.out.println("Enter Admin Password");
					String AdminPassword = sc.next();
					adminLogin = adminService.adminLogin(adminId, AdminPassword);

				} else {
					LoginException exception = new LoginException("LoginException:AdminId Doesnot Exist");
					exception.getMessage();
				}

				System.out.println("Admin Login :" + adminLogin);
				while (adminLogin) {
					System.out.println("<------------------------------------------->");
					System.out.println("Welcome to Admin Login Page");
					System.out.println("1. Add Bus   ");
					System.out.println("2.Search Bus  ");
					System.out.println("3.Update Bus Info   ");
					System.out.println("4.Delete Bus ");
					System.out.println("5.Bus Between   ");
					System.out.println("6.Logout   ");
					System.out.println("<------------------------------------------->");
					System.out.print("Enter Choice :");
					int adminChoice = sc.nextInt();
					
					/**Used for adding the bus details**/
					if (adminChoice == 1) {
						Bus bus2 = new Bus();
						System.out.println("Enter Bus Id");
						check = true;
						while (check) {
							String uid = service.checkUserIdAndBookinIdAndBusId((sc.next()));
							if (uid != null) {

								bus2.setBusId(Integer.parseInt(uid));
								check = false;
							} else {
								CustomException exception = new CustomException(
										"CustomException:BusId Should be Integer");
								exception.getMessage();

							}

						}
						Bus bus = adminService.searchBus(bus2.getBusId());
						if (bus != null) {
							CustomException exception = new CustomException("CustomException:BusId ALREADY Exist");
							exception.getMessage();
							break;
						} else {
							System.out.println("Enter Bus Name");
							bus2.setBusName(sc.next());
							System.out.println("bus Type");
							bus2.setBusType(sc.next());
							System.out.println("Source");
							bus2.setSource(sc.next());
							System.out.println("Destination");
							bus2.setDestination(sc.next());
							System.out.println("Total Seats ");
							check = true;
							while (check) {
								String uid = service.checkUserIdAndBookinIdAndBusId((sc.next()));
								if (uid != null) {

									bus2.setTotalSeats((Integer.parseInt(uid)));
									check = false;
								} else {
									CustomException exception = new CustomException(
											"CustomException:TotalSeats Should be Integer");
									exception.getMessage();
								}
							}
							System.out.println("Price");
							check = true;
							while (check) {
								String uid = service.checkUserIdAndBookinIdAndBusId((sc.next()));
								if (uid != null) {

									bus2.setPrice(Integer.parseInt(uid));
									check = false;
								} else {
									CustomException exception = new CustomException(
											"CustomException:Price Should be Number");
									exception.getMessage();
								}
							}
							Boolean create = adminService.createBus(bus2);
							if (create) {
								System.out.println("Bus Added Successfully");
								System.out.println("-----------------------------------------------------");
								Available available = new Available();
								System.out.println("Enter Avail iD");
								check = true;
								while (check) {
									String uid = service.checkUserIdAndBookinIdAndBusId((sc.next()));
									if (uid != null) {

										available.setAvailId(Integer.parseInt(uid));
										check = false;
									} else {
										CustomException exception = new CustomException(
												"CustomException:AvailId Should be Number");
										exception.getMessage();
									}
								}
								System.out.println("Enter Avail Seats");
								check = true;
								while (check) {
									String uid = service.checkUserIdAndBookinIdAndBusId((sc.next()));
									if (uid != null) {

										available.setAvailSeats(Integer.parseInt(uid));
										check = false;
									} else {
										CustomException exception = new CustomException(
												"CustomException:AvailSeats Should be Number");
										exception.getMessage();
									}
								}
								available.setBusId(bus2.getBusId());
								System.out.println("Enter Date");
								available.setJourneyDate(sc.next());
								if (adminService.addAvailability(available)) {
									System.out.println("Available added successfull");
								}
							} else {
								CustomException exception = new CustomException(
										"CustomException:Failed To Add Availability");
								exception.getMessage();
							}
						}
					}
					
					/**Used for searching bus details**/
					else if (adminChoice == 2) {
						System.out.println("Enter bus Id");
						check = true;
						while (check) {
							String uid = service.checkUserIdAndBookinIdAndBusId((sc.next()));
							if (uid != null) {

								busId = Integer.parseInt(uid);
								check = false;
							} else {
								CustomException exception = new CustomException("CustomException:Provide Proper BusId");
								exception.getMessage();
							}
						}
						Bus bus = adminService.searchBus(busId);
						if (bus != null) {
							System.out.println("Bus [busId=" + bus.getBusId() + ", Bus Name=" + bus.getBusName()
									+ ", source=" + bus.getSource() + ", destination=" + bus.getDestination()
									+ ", bus Type=" + bus.getBusType() + ", Seats=" + bus.getTotalSeats() + ", price="
									+ bus.getPrice() + "]");

						} else {
							CustomException exception = new CustomException("CustomException:No Bus Found");
							exception.getMessage();
						}
						
					/**Used for updating the bus information**/
					} else if (adminChoice == 3) {
						System.out.println("Enter bus Id");
						check = true;
						while (check) {
							String uid = service.checkUserIdAndBookinIdAndBusId((sc.next()));
							if (uid != null) {

								busId = Integer.parseInt(uid);
								check = false;
							} else {
								CustomException exception = new CustomException("CustomException:Provide Proper BusId");
								exception.getMessage();
							}
						}
						Bus bus = adminService.searchBus(busId);
						if (bus != null) {
							System.out.println("Enter Bus Name");
							bus.setBusName(sc.next());
							System.out.println("Bus Type");
							bus.setBusType(sc.next());
							System.out.println("Enter Source");
							bus.setSource(sc.next());
							System.out.println("Enter Destination");
							bus.setDestination(sc.next());
							System.out.println("Price");
							check = true;
							while (check) {
								String uid = service.checkUserIdAndBookinIdAndBusId((sc.next()));
								if (uid != null) {

									bus.setPrice(Integer.parseInt(uid));
									check = false;
								} else {
									CustomException exception = new CustomException(
											"CustomException:Price Should be Number");
									exception.getMessage();
								}
							}
							System.out.println("Total Seats ");
							check = true;
							while (check) {
								String uid = service.checkUserIdAndBookinIdAndBusId((sc.next()));
								if (uid != null) {

									bus.setTotalSeats((Integer.parseInt(uid)));
									check = false;
								} else {
									CustomException exception = new CustomException(
											"CustomException:TotalSeats Should be Integer");
									exception.getMessage();
								}
							}

							Boolean updateBus = adminService.updateBus(bus);
							if (updateBus) {
								System.out.println("Bus Info Updated");
							} else {
								UpdateFailedException exception = new UpdateFailedException(
										"UpdateFailedException:Provide Proper BusId");
								exception.getMessage();
							}

						} else {
							CustomException exception = new CustomException("CustomException:No Bus Found");
							exception.getMessage();
						}
						
					/**Used for deleting the bus details**/
					} else if (adminChoice == 4) {
						System.out.println("Enter bus Id");
						check = true;
						while (check) {
							String uid = service.checkUserIdAndBookinIdAndBusId((sc.next()));
							if (uid != null) {

								busId = Integer.parseInt(uid);
								check = false;
							} else {
								CustomException exception = new CustomException("CustomException:Provide Proper BusId");
								exception.getMessage();
							}
						}
						Bus bus = adminService.searchBus(busId);
						if (bus != null) {
							Boolean del = adminService.deletebus(busId);
							if (del) {
								System.out.println("Bus Deleted");
							} else {
								DeleteFailedException exception = new DeleteFailedException(
										"DeleteFailedException:Failed to Delete");
							}
						} else {
							CustomException exception = new CustomException("CustomException:No Bus Found");
							exception.getMessage();

						}
						
					/**Used for searching the bus route between source and destination**/	
					} else if (adminChoice == 5) {
						System.out.println("Enter Source");
						String source = sc.next();
						System.out.println("Enter Destination");
						String destination = sc.next();
						HashMap map = adminService.busBetween(source, destination);
						System.out.println(map);

					}
				/**Used for logging out from Admin's page**/
					else if (adminChoice == 6) {
						adminLogin = false;

					}

				}
			/**Used for exiting from the bus booking portal**/
			} else if (firstChoice == 10) {
				state = false;
			}

			else {
				System.out.println("Command Entered is Invalid");
				System.out.println("<------------------------------------------->");
			}
		}
	}

}
