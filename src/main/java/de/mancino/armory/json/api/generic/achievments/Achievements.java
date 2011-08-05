package de.mancino.armory.json.api.generic.achievments;

import java.util.List;

/**
{
  "achievements":{
    "achievementsCompleted":[6,7,8,9,10],
    "achievementsCompletedTimestamp":[1224283700000,1224283700000,1224283700000,1224283700000,1224283700000],
    "criteria":[34,35,36,37,38],
    "criteriaQuantity":[85,85,85,85,85],
    "criteriaTimestamp":[1309580447000,1309580447000,1309580447000,1309580447000,1309580447000],
    "criteriaCreated":[1309580447000,1309580447000,1309580447000,1309580447000,1309580447000]
  }
}
 * @author mmancino
 */
public class Achievements {
    /**
     * A list of achievement ids.
     */
    public List<Integer> achievementsCompleted;
    /**
     * A list of timestamps whose places correspond to the achievement ids in the "achievementsCompleted" list. 
     * The value of each timestamp indicates when the related achievement was earned.
     */
    public List<Long> achievementsCompletedTimestamp;
    /**
     * A list of criteria ids that can be used to determine the partial completedness of achievements.
     */
    public List<Long> criteria;
    /**
     * A list of values associated with a given achievement criteria. The position of a value corresponds to 
     * the position of a given achivement criteria.
     */
    public List<Long> criteriaQuantity;
    /**
     * A list of timestamps where the value represents when the criteria was considered complete. 
     * The position of a value corresponds to the position of a given achivement criteria.
     */
    public List<Long> criteriaTimestamp;
    /**
     * A list of timestamps where the value represents when the criteria was considered started. 
     * The position of a value corresponds to the position of a given achivement criteria.
     */
    public List<Long> criteriaCreated;
    

    }
