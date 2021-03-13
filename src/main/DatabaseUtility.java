package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;

public class DatabaseUtility
{

    public static void Import(File file, Company company){
        Scanner fileReader;
        try
        {
            fileReader = new Scanner(file);
        } catch ( FileNotFoundException e )
        {
            Controller.printError("Failed to open file");
            return;
        }

        int currentLine = 0;

        while(fileReader.hasNextLine()){
            currentLine++;
            String line = fileReader.nextLine();

            try{
                Employee newEmployee = Parse(line);
                if(newEmployee == null){
                    Controller.printError("Invalid employee line at " + currentLine + " - '"+line+"'");
                    fileReader.close();
                    return;
                }
                company.add(newEmployee);
            }catch(Exception e){
                Controller.printError("Exception - Invalid employee line at " + currentLine + " - '"+line+"'");
                fileReader.close();
                return;
            }


        }
        fileReader.close();

        Controller.print( "Successfully imported data!");
    }

    public static Employee Parse(String line){
        String[] tokens = line.split(",");

        if(tokens.length < 5) return null;

        Date date = new Date(tokens[3]);

        if(!date.isValid()) return null;

        String name = tokens[1].replace(' ',',');
        String[] nameTokens = name.split(",");

        if(nameTokens.length > 1){
            name = "";
            for(int i = 1; i < nameTokens.length; i++){
                name += nameTokens[i] + ",";
            }

            name += nameTokens[0];
        }

        Profile profile = new Profile(name, tokens[2], date );
        if(!profile.isDepartmentValid() || !profile.isNameValid()) return null;


        switch(tokens[0]){
            case "P":
                float hourlyRate = 0;
                try{
                    hourlyRate = Float.parseFloat(tokens[4]);
                }catch ( Exception e ){
                    return null;
                }

                return new Parttime(profile, hourlyRate);
            case "F":
                float salary = 0;
                try{
                    salary = Float.parseFloat(tokens[4]);
                }catch ( Exception e ){
                    return null;
                }


                return new Fulltime(profile, salary);
            case "M":
                if(tokens.length < 6){
                    return null;
                }

                float mSalary = 0;
                int  mType = 0;
                try{
                    mSalary = Float.parseFloat(tokens[4]);
                    mType = Integer.parseInt(tokens[5]);
                }catch ( Exception e ){
                    return null;
                }

                return new Management(profile, mSalary, mType);
        }
        Controller.printError("Failed 1");
        return null;
    }

    public static void Export(File file, Company company){
        if(!file.exists()){
            try{
                file.createNewFile();
            }catch(Exception e){
                Controller.printError( "Failed to create new file");
            }
        }

        try{
            FileWriter fileWriter = new FileWriter(file, false);

            fileWriter.write(company.exportDatabase());

            fileWriter.flush();
            fileWriter.close();
        }catch(Exception e){
            Controller.printError( "Failed to write to file");
        }



        Controller.print( "Successfully exported!");
    }

}
