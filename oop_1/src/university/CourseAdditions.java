package src.university;
// =====================================================================
// Добавить/заменить эти методы в существующем Course.java
// =====================================================================

/*
 * 1. Метод generateReport() — статистика по курсу:
 */

//public String generateReport() {
//    if (students.isEmpty()) return "=== " + name + ": No students enrolled ===";
//    DoubleSummaryStatistics stats = students.stream()
//            .map(s -> s.getMark(this))
//            .filter(Objects::nonNull)
//            .mapToDouble(Mark::getTotal)
//            .summaryStatistics();
//    if (stats.getCount() == 0) return "=== " + name + ": No marks recorded ===";
//    long passed = students.stream()
//            .map(s -> s.getMark(this))
//            .filter(m -> m != null && m.isPassed())
//            .count();
//    return String.format(
//        "=== Report: %s ===%n  Enrolled : %d%n  Graded   : %d%n" +
//        "  Avg      : %.2f%n  Max      : %.2f%n  Min      : %.2f%n  Passed   : %d/%d",
//        name, students.size(), (int)stats.getCount(),
//        stats.getAverage(), stats.getMax(), stats.getMin(),
//        passed, (int)stats.getCount());
//}

/*
 * 2. В Manager.approveRegistration нужно удалять из pendingStudents.
 *    getPendingStudents() должен возвращать изменяемый список, либо
 *    добавить метод removePendingStudent():
 */

//public void removePendingStudent(Student s) {
//    pendingStudents.remove(s);
//}

/*
 * 3. Добавить getter yearOfStudy:
 */
//public int getYearOfStudy() { return yearOfStudy; }
