package com.project.SSPS.controller;

import com.project.SSPS.dto.PrinterDTO;
import com.project.SSPS.service.ISpsoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/v1/spso/")
@RequiredArgsConstructor
public class SpsoController {
    private final ISpsoService spsoService ;

//    @GetMapping("{id}")
//    public ResponseEntity<SpsoResponse> findSpsoInfo(@PathVariable String id){
//        return ResponseEntity.ok(spsoService.findSpsoInfo(id));
//    }

    @PostMapping("printer")
    public ResponseEntity<?> createPrinter(@RequestBody PrinterDTO printerDTO){
        try {
            return ResponseEntity.ok(spsoService.createPrinter(printerDTO));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    //get all printer info
    @GetMapping("printers")
    public ResponseEntity<?> getAllprinters(){
        try {
            return ResponseEntity.ok(spsoService.getAllPrinters());
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("getPrinterById")
    public ResponseEntity<?> getPrinter(@RequestParam String printer_id){
        try {
            return ResponseEntity.ok(spsoService.getPrinterById(printer_id));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //get all printing history
    @GetMapping("print")
    public ResponseEntity<?> getAllPrintRequests() {
        return ResponseEntity.ok(spsoService.getAllPrintRequests() );
    }

    @GetMapping("page")
    public ResponseEntity<?> getAllPurchasedPages(@RequestParam String printer_id){
        return ResponseEntity.ok(spsoService.getPrinterById(printer_id));
    }

//    @GetMapping("statistic")
//    public ResponseEntity<?> getAllPurchasedPages(@RequestParam String printer_id){
//        return ResponseEntity.ok(spsoService.getPrinterById(printer_id));
//    }
//    @GetMapping("/getAllRequestOnPrinter/{printer_id}")
//    public ResponseEntity<?> getAllPrintRequestsOnPrinter(@PathVariable String printer_id){
//        return ResponseEntity.ok(spsoService.getAllPrintRequestsByPrinterId(printer_id));
//    }
//
//    @GetMapping("/getAllRequestOnStudent/{std_id}")
//    public ResponseEntity<?> getAllPrintRequestsOnStudent(@PathVariable String std_id){
//       return ResponseEntity.ok(spsoService.getAllPrintRequestsByStudentId(std_id));
//    }
//    @GetMapping("/getOverall")
//    public ResponseEntity<?> getOverall(){
//        return ResponseEntity.ok(spsoService.getOverall());
//    }
//
//    @PostMapping("/insertNewPrinter")
//    public ResponseEntity<?> insertNewPrinter(
//            @RequestParam String building,
//            @RequestParam String model,
//            @RequestParam String importDateString) throws ParseException {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        Date utilDate = sdf.parse(importDateString);
//        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
//        return new ResponseEntity<>(spsoService.insertNewPrinter(building,model,sqlDate),HttpStatus.OK);
//    }
//    @PostMapping("/new")
//    public ResponseEntity<?>newsemester(
//            @RequestParam String semester
//            ,@RequestParam Integer numOfPaperDefault)
//    {
//        return new ResponseEntity<>(spsoService.reset(semester,numOfPaperDefault),
//                HttpStatus.OK);
//
//    }
//
//    @PutMapping("/setPrinterState")
//    public ResponseEntity<?> setPrinterState (
//            @RequestParam String printer_id
//    ){
//        return new ResponseEntity<>(spsoService.updateStatePrinter(printer_id),HttpStatus.OK);
//    }
//    @PutMapping("/acceptPrintRequest")
//    public ResponseEntity<?> acceptPrintRequest (
//            /*@RequestParam String printer_id,*/ @RequestParam String file_id, @RequestParam Integer orderNum
//    ){
//        return new ResponseEntity<>(spsoService.acceptPrint(/*printer_id,*/file_id,orderNum),HttpStatus.OK);
//    }
//
//    // status 0 dang cho  , status 1 la tu choi , status 2 la chap nhan
//    @PutMapping("/refusePrintRequest")
//    public ResponseEntity<?> refusePrintRequest(
//            @RequestParam Integer order_num ,
//            @RequestParam String file_id
//    ){
//        return new ResponseEntity<>(spsoService.refusePrint(order_num,file_id, 1),HttpStatus.OK);
//    }


}
