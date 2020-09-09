package com.hemmels.sainstest.runtime;

import java.util.List;

import com.hemmels.sainstest.pojo.ShelfItem;

public class ConsolePrinter {

	public void printToConsole(List<ShelfItem> items, float total)
	{
		String output = JsonFormatter.createJsonOutput(items, total);
		System.out.print(output);
	}

}
