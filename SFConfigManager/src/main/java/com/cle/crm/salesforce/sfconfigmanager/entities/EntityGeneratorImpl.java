package com.cle.crm.salesforce.sfconfigmanager.entities;


import com.cle.crm.salesforce.sfconfigmanager.utils.Const;
import com.cle.crm.salesforce.sfconfigmanager.utils.PathUtils;
import com.test.cle.salesforce.yamlelements.security.DataType;
import com.test.cle.salesforce.yamlelements.security.EntityDef;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellUtil;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.*;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class EntityGeneratorImpl implements IEntityGenerator {
    final static int WORKSHEET_MAX_NAME_LENGTH = 31;

    static Logger log = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());
    public Workbook workbook;
    List<String> tabList;
    String spreadsheet;

    public List getTabList() {

        return tabList;
    }

    public String getSpreadsheet() {
        return spreadsheet;
    }

    @Override
    public EntityDef doSerialise(String entityYamlFile) {
        Yaml yaml = new Yaml(new Constructor(EntityDef.class));
        EntityDef entity = null;
        try (InputStream inputStream = new FileInputStream(new File(entityYamlFile))) {

            entity = yaml.load(inputStream);
        } catch (IOException e) {
            log.error(e);
        }
        return entity;
    }


    private void writeEntityValues(Sheet sheet, BufferedWriter writer, String category) {

        for (int rowIndex = 1; rowIndex < sheet.getPhysicalNumberOfRows(); rowIndex++) {
            String name = "";
            String value = "";
            try {
                Row row = CellUtil.getRow(rowIndex, sheet);
                DataFormatter formatter = new DataFormatter();
                Cell cellId = CellUtil.getCell(row, Const.ENTITY_ATTR_NAME_COL);
                name = formatter.formatCellValue(cellId);

                switch (category) {
                    case Const.YAML_HEADER_DATA_TYPE:
                        value = CellUtil.getCell(row, Const.ENTITY_ATTR_DATA_TYPE_COL).getStringCellValue();
                        break;
                    case Const.YAML_HEADER_EN_LABEL:
                        value = formatter.formatCellValue(CellUtil.getCell(row, Const.ENTITY_ATTR_EN_LABEL_COL));
                        break;
                    case Const.YAML_HEADER_DE_LABEL:
                        value = formatter.formatCellValue(CellUtil.getCell(row, Const.ENTITY_ATTR_DE_LABEL_COL));
                        break;
                    case Const.YAML_HEADER_EN_PL:
                        value = formatter.formatCellValue(CellUtil.getCell(row, Const.ENTITY_ATTR_EN_PL_COL));
                        break;
                    case Const.YAML_HEADER_DE_PL:
                        value = formatter.formatCellValue(CellUtil.getCell(row, Const.ENTITY_ATTR_DE_PL_COL));
                        break;
                    case Const.YAML_HEADER_WORKBENCH:
                        value = formatter.formatCellValue(CellUtil.getCell(row, Const.ENTITY_ATTR_WORKBENCH_COL));
                        break;
                    case Const.YAML_HEADER_REMOVALS:
                        value = formatter.formatCellValue(CellUtil.getCell(row, Const.ENTITY_ATTR_REMOVAL_COL));
                        break;
                    case Const.YAML_HEADER_MANDATORY:
                        value = formatter.formatCellValue(CellUtil.getCell(row, Const.ENTITY_ATTR_MANDATORY_COL));
                        break;
                    case Const.YAML_HEADER_ENCRYPTED:
                        value = formatter.formatCellValue(CellUtil.getCell(row, Const.ENTITY_ATTR_ENCRYPTED_COL));
                        break;
                    case Const.YAML_HEADER_RESTRICTED:
                        value = formatter.formatCellValue(CellUtil.getCell(row, Const.ENTITY_ATTR_RESTRICTED_COL));
                        break;
                    default:
                        System.out.println("Unrecognised header: " + category);
                }

                value = validateValue(value);

                if (isFlagAttribute(category)) {
                    if (value.equalsIgnoreCase("Yes")) {
                        writer.write("  - name: " + name);
                        writer.write("\r\n");
                    }
                } else {
                    /*
                    if(isPicklistAttribute(category)) {
                        validatePicklistAttribute(row, category, name, value);
                    }
                    */

                    if (isPicklistAttribute(category)) {
                        if (!value.isEmpty()) writeNameValuePair(writer, name, value);
                    } else {
                        writeNameValuePair(writer, name, value);
                    }
                }
            } catch (IOException e) {
                log.error(e);
            }

        }

    }

    private String validateValue(String value) {

        if (value.equals("na")) {
            value = value.replace("na", "");
        } else if (value.equals("nana")) {
            value = value.replace("nana", "");

        } else if (value.equals("No") || value.equals("no")) {
            value = value.replace("No", "");
            value = value.replace("no", "");
        }

        return value;
    }

    private boolean isFlagAttribute(String category) {
        return category.equalsIgnoreCase(Const.YAML_HEADER_MANDATORY) ||
                category.equalsIgnoreCase(Const.YAML_HEADER_REMOVALS) ||
                category.equalsIgnoreCase(Const.YAML_HEADER_ENCRYPTED) ||
                category.equalsIgnoreCase(Const.YAML_HEADER_RESTRICTED);
    }

    private boolean isPicklistAttribute(String category) {
        return category.equalsIgnoreCase(Const.YAML_HEADER_EN_PL) ||
                category.equalsIgnoreCase(Const.YAML_HEADER_DE_PL);
    }

    private void validatePicklistAttribute(Row row, String category, String name, String value) {
        String dataType = CellUtil.getCell(row, Const.ENTITY_ATTR_DATA_TYPE_COL).getStringCellValue();
        boolean isPicklist = dataType.equalsIgnoreCase("picklist");

        if (isPicklist && value.isEmpty()) {
            throw new RuntimeException(
                    String.format("Picklist %s is missing values for %s", name, category));
        }

        if (!isPicklist && !value.isEmpty()) {
            throw new RuntimeException(
                    String.format("%s (datatype: %s) is not a picklist, but has values for %s",
                            name, dataType, category));
        }
    }

    private void writeNameValuePair(BufferedWriter writer, String name, String value) throws IOException {
        if (value.isEmpty()) return;

        writer.write("  - name: " + name);
        writer.write("\r\n");

        writer.write("    value: " + validateValue(value));
        writer.write("\r\n");
    }


    @Override
    public String doGenerateEntityYamlFile(String entityName) {
        String worksheetName = entityName;

        if(worksheetName.length() >= WORKSHEET_MAX_NAME_LENGTH) {
            String newWorksheetNameName = worksheetName.substring(0, WORKSHEET_MAX_NAME_LENGTH);
            log.warn(String.format("Worksheet name '%s' exeeds name length limits and was shortened to '%s'.",
                    worksheetName,
                    newWorksheetNameName));
            worksheetName = newWorksheetNameName;
        }

        boolean tabExists = false;
        String targetFile = PathUtils.getTestResourcesDirectory() + "/" + entityName + ".1.yaml";
        File f = new File(targetFile);
        if (f.exists()) {
            boolean deleted = f.delete();

            if (!deleted) {
                log.error("Failed to delete " + targetFile);
            }
        }
        //get list of sheet names in workbook
        //if the entity name in the arg list is a valid tab namein the workbook then write the yaml file
        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
            String sheetName = workbook.getSheetAt(i).getSheetName().toLowerCase();
            if (worksheetName.toLowerCase().equalsIgnoreCase(sheetName)) {
                tabExists = true;
                break;
            }
        }

        if (tabExists) {
            try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(targetFile))) {
                Sheet sheet = workbook.getSheet(worksheetName);

                writer.write("name: " + entityName);
                writer.write("\r\n");

                writer.write(Const.YAML_HEADER_DATA_TYPE);
                writer.write("\r\n");
                writeEntityValues(sheet, writer, Const.YAML_HEADER_DATA_TYPE);

                writer.write(Const.YAML_HEADER_EN_LABEL);
                writer.write("\r\n");
                writeEntityValues(sheet, writer, Const.YAML_HEADER_EN_LABEL);

                writer.write(Const.YAML_HEADER_DE_LABEL);
                writer.write("\r\n");
                writeEntityValues(sheet, writer, Const.YAML_HEADER_DE_LABEL);

                writer.write(Const.YAML_HEADER_EN_PL);
                writer.write("\r\n");
                writeEntityValues(sheet, writer, Const.YAML_HEADER_EN_PL);

                writer.write(Const.YAML_HEADER_DE_PL);
                writer.write("\r\n");
                writeEntityValues(sheet, writer, Const.YAML_HEADER_DE_PL);

                /*
                writer.write(Const.YAML_HEADER_WORKBENCH);
                writer.write("\r\n");
                writeEntityValues(sheet, writer, Const.YAML_HEADER_WORKBENCH);
                */

                writer.write(Const.YAML_HEADER_REMOVALS);
                writer.write("\r\n");
                writeEntityValues(sheet, writer, Const.YAML_HEADER_REMOVALS);

                writer.write(Const.YAML_HEADER_MANDATORY);
                writer.write("\r\n");
                writeEntityValues(sheet, writer, Const.YAML_HEADER_MANDATORY);

                writer.write(Const.YAML_HEADER_ENCRYPTED);
                writer.write("\r\n");
                writeEntityValues(sheet, writer, Const.YAML_HEADER_ENCRYPTED);

                writer.write(Const.YAML_HEADER_RESTRICTED);
                writer.write("\r\n");
                writeEntityValues(sheet, writer, Const.YAML_HEADER_RESTRICTED);
            } catch (IOException e) {
                log.error(e);
            }
            finally {
                try {
                    workbook.close();
                }
                catch (IOException e) {
                    log.error(e);
                }
            }
        } else {
            String errMsg = "Tab " + entityName + " does not exist on spreadsheet " + spreadsheet;
            log.error(errMsg);
            throw new Error(errMsg);
        }
        return targetFile;

    }

    /**
     * Setup the tablist and the workbook
     *
     * @param spreadsheetTabs
     */

    @Override
    public void doInit(String spreadsheetTabs) {

        PathUtils pathUtils = new PathUtils();

        spreadsheet = PathUtils.getMainResourcesDirectory()
                + "/SFEntities.xlsm";

        if (!pathUtils.fileExists(spreadsheet)) {

            log.error("File does not exist " + spreadsheet);

        } else {

            tabList = Arrays.asList(spreadsheetTabs.split(","));
            try {
                workbook = WorkbookFactory.create(new File(spreadsheet));
            } catch (IOException e) {
                log.error(e);
            }

        }
    }
}