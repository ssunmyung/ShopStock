package project;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class InventoryManagement {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		ArrayList<Product> productList = new ArrayList<Product>();
		int choice;
		do {
			System.out.println("\n<재고관리>");
			System.out.println("1. 상품 등록");
			System.out.println("2. 상품 정보 수정");
			System.out.println("3. 상품 삭제");
			System.out.println("4. 재고량 확인");
			System.out.println("5. 판매 기록 관리");
			System.out.println("6. 입고 물량");
			System.out.println("0. 종료");
			System.out.print("원하는 메뉴를 선택하세요: ");
			choice = scanner.nextInt();
			switch (choice) {
			case 1:
				registerProduct(productList);
				break;
			case 2:
				updateProduct(productList);
				break;
			case 3:
				deleteProduct(productList);
				break;
			case 4:
				checkInventory(productList);
				break;
			case 5:
				manageSales(productList);
				break;
			case 6:
				manageStock(productList);
				break;
			case 0:
				System.out.println("프로그램을 종료합니다.");
				break;
			default:
				System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
			}
		} while (choice != 0);
		scanner.close();
	}

	//상품 등록
	public static void registerProduct(ArrayList<Product> productList) {
		Scanner scanner = new Scanner(System.in);
		System.out.print("상품명: ");
		String name = scanner.nextLine();
		System.out.print("가격: ");
		int price = scanner.nextInt();
		System.out.print("재고량: ");
		int stock = scanner.nextInt();
		productList.add(new Product(name, price, stock));
		System.out.println(name + "이(가) 등록되었습니다.");

		try {
			FileWriter writer = new FileWriter("C:\\Users\\Kosmo\\Desktop\\test.txt", true);
			writer.write(name + " " + price + " " + stock + "\n");
			writer.close();
		} catch (IOException e) {
			System.out.println("파일 쓰기 오류: " + e.getMessage());
		}
	}

	// 상품 정보 수정
	public static void updateProduct(ArrayList<Product> productList) {
		Scanner scanner = new Scanner(System.in);
		System.out.print("수정할 상품명: ");
		String name = scanner.nextLine();
		for (Product product : productList) {
			if (product.getName().equals(name)) {
				System.out.print("수정할 가격: ");
				int price = scanner.nextInt();
				System.out.print("수정할 재고량: ");
				int stock = scanner.nextInt();
				product.setPrice(price);
				product.setStock(stock);
				System.out.println(name + "의 정보가 수정되었습니다.");
				try {
					FileWriter writer = new FileWriter("C:\\Users\\Kosmo\\Desktop\\test.txt");
					for (Product p : productList) {
						writer.write(p.getName() + " " + p.getPrice() + " " + p.getStock() + "\n");
					}
					writer.close();
				} catch (IOException e) {
					System.out.println("파일 저장 중 오류가 발생했습니다.");
				}
				return;
			}
		}
		System.out.println(name + "을(를) 찾을 수 없습니다.");
	}

	// 상품 삭제
	public static void deleteProduct(ArrayList<Product> productList) {
		Scanner scanner = new Scanner(System.in);
		System.out.print("삭제할 상품명: ");
		String name = scanner.nextLine();
		try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Kosmo\\Desktop\\test.txt"))) {
			String line;
			ArrayList<String> newLines = new ArrayList<>();
			boolean found = false;
			while ((line = br.readLine()) != null) {
				String[] parts = line.split(" ");
				if (parts[0].equals(name)) {
					found = true;
				} else {
					newLines.add(line);
				}
			}
			if (found == true) {
				try (PrintWriter writer = new PrintWriter(new FileWriter("C:\\Users\\Kosmo\\Desktop\\test.txt"))) {
					for (String newLine : newLines) {
						writer.println(newLine);
					}
				}
				System.out.println(name + "이 삭제되었습니다.");
			} else {
				System.out.println(name + "을(를) 찾을 수 없습니다.");
			}
		} catch (IOException e) {
			System.out.println("파일을 읽는 중에 오류가 발생했습니다.");
		}
	}

	// 재고량 확인
	public static void checkInventory(ArrayList<Product> productList) {
        try (Scanner scanner = new Scanner(new File("C:\\Users\\Kosmo\\Desktop\\test.txt"))) {
            System.out.println("[상품명   가격   재고량]");
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] data = line.split(" ");
                String name = data[0];
                int price = Integer.parseInt(data[1]);//문자열 타입 integer 타입으로 전환
                int stock = Integer.parseInt(data[2]);
                Product product = new Product(name, price, stock);
                productList.add(product);
                System.out.println("[" + product.getName() + " " + product.getPrice() + " " + product.getStock() + "]");
            }
        } catch (FileNotFoundException e) {
            System.out.println("파일을 찾을 수 없습니다.");
        }
    }

	// 판매 기록 관리
	public static void manageSales(ArrayList<Product> productList) {
		Scanner scanner = new Scanner(System.in);
		System.out.print("판매할 상품명: ");
		String name = scanner.nextLine();
		for (Product product : productList) {
			if (product.getName().equals(name)) {
				System.out.print("판매량: ");
				int quantity = scanner.nextInt();
				if (product.sell(quantity)) {
					System.out.println(name + "을(를) " + quantity + "개 판매하였습니다.");
					try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Kosmo\\Desktop\\test.txt"))) {
						String line;
						ArrayList<String> newLines = new ArrayList<>();
						boolean found = false;
						while ((line = br.readLine()) != null) {
							String[] parts = line.split(" ");
							if (parts[0].equals(name)) {
								found = true;
							} else {
								newLines.add(line);
							}
						}
						if (found == true) {
							try (PrintWriter writer = new PrintWriter(new FileWriter("C:\\Users\\Kosmo\\Desktop\\test.txt"))) {
								for (String newLine : newLines) {
									writer.println(newLine);
								}
							}
							FileWriter writer = new FileWriter("C:\\Users\\Kosmo\\Desktop\\test.txt", true);
							writer.write(name + " " + product.getPrice() + " " + product.getStock());
							writer.close();
						} else {
							System.out.println(name + "의 재고가 부족합니다.");
						}
						return;
					}catch (IOException e) {
						System.out.println("파일 쓰기 오류: " + e.getMessage());
					}
					return;
				}
			}
		}
	}

	// 입고 물량
	public static void manageStock(ArrayList<Product> productList) {
		Scanner scanner = new Scanner(System.in);
		System.out.print("입고할 상품명: ");
		String name = scanner.nextLine();
		for (Product product : productList) {
			if (product.getName().equals(name)) {
				System.out.print("입고량: ");
				int quantity = scanner.nextInt();
				product.addStock(quantity);
				System.out.println(name + "의 재고가 " + quantity + "개 증가하였습니다.");
				try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Kosmo\\Desktop\\test.txt"))) {
					String line;
					ArrayList<String> newLines = new ArrayList<>();
					boolean found = false;
					while ((line = br.readLine()) != null) {
						String[] parts = line.split(" ");
						if (parts[0].equals(name)) {
							found = true;
						} else {
							newLines.add(line);
						}
					}
					if (found == true) {
						try (PrintWriter writer = new PrintWriter(new FileWriter("C:\\Users\\Kosmo\\Desktop\\test.txt"))) {
							for (String newLine : newLines) {
								writer.println(newLine);
							}
						}
						FileWriter writer = new FileWriter("C:\\Users\\Kosmo\\Desktop\\test.txt", true);
						writer.write(name + " " + product.getPrice() + " " + product.getStock());
						writer.close();
					} else {
						System.out.println(name + "의 재고가 부족합니다.");
					}
					return;
				}catch (IOException e) {
					System.out.println("파일 쓰기 오류: " + e.getMessage());
				}
				return;
			}
		}
		System.out.println(name + "을(를) 찾을 수 없습니다.");
	}
}
