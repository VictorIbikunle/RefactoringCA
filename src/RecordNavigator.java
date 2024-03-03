import java.io.File;

public class RecordNavigator {
    private RandomFile application;
    private File file;
    private long currentByteStart;
    private Employee currentEmployee;

    public RecordNavigator(RandomFile application, File file) {
        this.application = application;
        this.file = file;
        this.currentByteStart = 0;
        this.currentEmployee = null;
    }

    public void firstRecord() {
        if (isSomeoneToDisplay()) {
            openReadFile();
            currentByteStart = application.getFirst();
            setCurrentEmployeeAndMoveToNext();
            closeReadFile();
        }
    }

    public void previousRecord() {
        if (isSomeoneToDisplay()) {
            openReadFile();
            currentByteStart = application.getPrevious(currentByteStart);
            setCurrentEmployeeAndMoveToNextUntilActive();
            closeReadFile();
        }
    }

    public void nextRecord() {
        if (isSomeoneToDisplay()) {
            openReadFile();
            currentByteStart = application.getNext(currentByteStart);
            setCurrentEmployeeAndMoveToNextUntilActive();
            closeReadFile();
        }
    }

    public void lastRecord() {
        if (isSomeoneToDisplay()) {
            openReadFile();
            currentByteStart = application.getLast();
            setCurrentEmployeeAndMoveToPreviousUntilActive();
            closeReadFile();
        }
    }

    private void openReadFile() {
        application.openReadFile(file.getAbsolutePath());
    }

    private void closeReadFile() {
        application.closeReadFile();
    }

    private void setCurrentEmployeeAndMoveToNext() {
        currentEmployee = application.readRecords(currentByteStart);
        if (currentEmployee.getEmployeeId() == 0) {
            nextRecord();
        }
    }

    private void setCurrentEmployeeAndMoveToNextUntilActive() {
        setCurrentEmployeeAndMoveToNext();
        while (currentEmployee.getEmployeeId() == 0) {
            setCurrentEmployeeAndMoveToNext();
        }
    }

    private void setCurrentEmployeeAndMoveToPreviousUntilActive() {
        setCurrentEmployeeAndMoveToPrevious();
        while (currentEmployee.getEmployeeId() == 0) {
            setCurrentEmployeeAndMoveToPrevious();
        }
    }

    private void setCurrentEmployeeAndMoveToPrevious() {
        currentEmployee = application.readRecords(currentByteStart);
        if (currentEmployee.getEmployeeId() == 0) {
            previousRecord();
        }
    }

    private boolean isSomeoneToDisplay() {
        return application.isSomeoneToDisplay();
    }
}

