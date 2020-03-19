import UIKit

class ViewLogTableViewController: UITableViewController {

    var sleepLog: SleepLog?
    
    @IBOutlet weak var dateLabel: UILabel!
    @IBOutlet weak var lengthLabel: UILabel!
    @IBOutlet weak var relaxationLabel: UILabel!
    override func viewWillAppear(_ animated: Bool) {
        dateLabel.text = sleepLog?.getDate()
        lengthLabel.text = "\(sleepLog?.length ?? 0.0) hours"
        relaxationLabel.text = sleepLog?.relaxation
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
    }

}
