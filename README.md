# snowplow-event-recovery
## Overview
> Snowplow Event Recovery allows you to fix Snowplow bad rows and make them ready for reprocessing.
> You will typically run the Snowplow Event Recovery jar as part of an EMR jobflow with three steps:

1.S3DistCp to move the event files you want to reprocess from S3 to HDFS. This step uses the groupBy option to combine small files into larger blocks, speeding up the job.
2.Hadoop Event Recovery itself. This step uses your custom JavaScript to choose which events to reprocess and how to change them.
3.Another S3DistCp step to move the recovered events from HDFS to S3.
