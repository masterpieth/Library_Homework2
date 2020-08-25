import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UIManager {
	
	private BookManager bookManager = new BookManager();
	private Scanner scanner = new Scanner(System.in);
	private String input;

	public void exec() {
		bookManager.setBookList();
		while(true) {
			printMainMenu();
			input = scanner.nextLine();
			input = input.trim();
			int choice = 0;
			try {
				choice = Integer.parseInt(input);
			} catch (NumberFormatException e) {
				if(input.equals("exit")) {
					System.out.println("終了しました。\nありがとうございます。");
					break;
				} else {
					System.out.println("間違いました。もう一度入力ください。");
				}
			}
			switch(choice) {
			case 1:
				searchBook();
				break;
			case 2:
				getBookList();
				break;
			default:
				break;
			}
		}
	}
	public void printMainMenu() {
		System.out.println("１．検索");
		System.out.println("２．出力");
		System.out.println("exit: 終了");
		System.out.print("input:");
	}
	
	public void searchBook() {
		List<Book> result = new ArrayList<>();
		while(true) {
			System.out.println("検索(exit:戻る)");
			System.out.println("タイトル又は著者を入力してください。");
			System.out.print("input : ");
			input = scanner.nextLine();
			input = input.trim();
			if(input.equals("exit")) break;
			
			result = bookManager.searchBook(input);

			if(result.size() == 0) continue;
			
			System.out.println("タイトル | 著者 | 発売日 | 値段");
			result.forEach(i -> {
				System.out.printf("%s | %s | %s | %d\n",
						i.getTitle(),i.getAuthor(),i.getDate(),i.getPrice());
			});
			System.out.println("-----------------------------------");
		}
	}
	
	public void getBookList() {
		List<Book> result = new ArrayList<>();
		while(true) {
			System.out.println("出力(exit:戻る)");
			System.out.println("1.タイトル順");
			System.out.println("2.発売日順");
			System.out.println("3.値段順");
			System.out.print("input : ");
			input = scanner.nextLine();
			input = input.trim();
			int choice = 0;
			try {
				choice = Integer.parseInt(input);
			} catch (NumberFormatException e) {
				if(input.equals("exit")) break;
				System.out.println("正しい番号を入力してください。");
				continue;
			}
			
			result = bookManager.getBookList(choice);
			
			System.out.println("タイトル | 著者 | 発売日 | 値段");
			result.forEach(i -> {
				System.out.printf("%s | %s | %s | %d\n",
						i.getTitle(),i.getAuthor(),i.getDate(),i.getPrice());
			});
			System.out.println("-----------------------------------");
		}
	}
}
