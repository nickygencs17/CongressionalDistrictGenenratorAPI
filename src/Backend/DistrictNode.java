/**
 *
 * @author Rahul S Agasthya
 */
public class DistrictNode {
    VoterDistrict district;
    DistrictNode next;

    public DistrictNode(VoterDistrict district, DistrictNode next) {
        this.district = district;
        this.next = next;
    }

    public VoterDistrict getDistrict() {
        return district;
    }

    public void setDistrict(VoterDistrict district) {
        this.district = district;
    }

    public DistrictNode getNext() {
        return next;
    }

    public void setNext(DistrictNode next) {
        this.next = next;
    }
}
