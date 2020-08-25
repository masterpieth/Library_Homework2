import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class BookManager implements DataReader{
	
	private List<Book> bookList;
	private BufferedReader bufferedReader;
	
	public void setBookList() {
		try {
			bookList = new ArrayList<>();
			bufferedReader = Files.newBufferedReader(Paths.get("src/lib/図書リスト.csv"));
			String line = "";
			
			while((line = bufferedReader.readLine()) != null) {
				List<String> tempList = new ArrayList<>();
				String[] array = line.split(",");
				tempList = Arrays.asList(array);
				try {
					int price = Integer.parseInt(tempList.get(3));
					
					String[] dateStr = tempList.get(2).split("/");
					List<Integer> dateInt = new ArrayList<>();
					for(String str: dateStr) {
						int temp  = Integer.parseInt(str);
						dateInt.add(temp);
					}
					LocalDate date = LocalDate.of(dateInt.get(0), dateInt.get(1), dateInt.get(2));
					
					Book book = new Book(tempList.get(0), tempList.get(1), date, price);
					bookList.add(book);
				} catch(NumberFormatException e) {
					continue;
				} catch(DateTimeParseException e) {
					continue;
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(bufferedReader != null) {
					bufferedReader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public List<Book> searchBook(String input) {
		List<Book> result = bookList.stream()
		.filter(book -> book.getTitle().contains(input) || book.getAuthor().contains(input))
		.collect(Collectors.toList());
		return result;
	}
	
	public List<Book> getBookList(int choice) {
		List<Book> result = new ArrayList<Book>();
		switch (choice) {
		case 1:
			result = bookList.stream()
			.sorted(Comparator.comparing(Book::getTitle))
			.collect(Collectors.toList());
			break;
		case 2:
			result = bookList.stream()
			.sorted(Comparator.comparing(Book::getDate))
			.collect(Collectors.toList());
			break;
		case 3:
			result = bookList.stream()
			.sorted(Comparator.comparing(Book::getPrice))
			.collect(Collectors.toList());
			break;
		}
		return result;
	}
}
